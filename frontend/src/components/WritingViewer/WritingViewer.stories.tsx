import { Meta, StoryObj } from '@storybook/react';
import { rest } from 'msw';
import WritingViewer from './WritingViewer';
import {
  StoryContainer,
  StoryItemContainer,
  StoryItemContainerRow,
  StoryItemTitle,
} from 'styles/storybook';
import { writingURL } from 'constants/apis/url';
import { GetWritingResponse } from 'types/apis/writings';
import { writingContentMock } from 'mocks/writingContentMock';

const meta = {
  title: 'WritingViewer',
  component: WritingViewer,
  args: {
    writingId: 1,
  },
  argTypes: {
    writingId: {
      description: '`writingId`에 해당하는 글을 서버에서 받아옵니다',
    },
  },
} satisfies Meta<typeof WritingViewer>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Success: Story = {
  render: ({ writingId }) => {
    return (
      <StoryContainer>
        <StoryItemContainer>
          <StoryItemTitle>글 가져오기 성공</StoryItemTitle>
          <WritingViewer writingId={writingId}></WritingViewer>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
Success.parameters = {
  msw: {
    handlers: [
      rest.get(`${writingURL}/:writingId`, (req, res, ctx) => {
        const writingId = Number(req.params.writingId);

        return res(
          ctx.delay(300),
          ctx.status(200),
          ctx.json<GetWritingResponse>({
            id: writingId,
            title: '테스트 글 제목',
            content: writingContentMock,
          }),
        );
      }),
    ],
  },
};

export const Failure: Story = {
  render: ({ writingId }) => {
    return (
      <StoryContainer>
        <StoryItemContainer>
          <StoryItemTitle>글 가져오기 실패</StoryItemTitle>
          <WritingViewer writingId={writingId}></WritingViewer>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
Failure.parameters = {
  msw: {
    handlers: [
      rest.get(`${writingURL}/:writingId`, (req, res, ctx) => {
        return res(ctx.delay(300), ctx.status(404));
      }),
    ],
  },
};

// TODO: 프로젝트 초기 설정 안정화되면 위 handlers 리팩터링
