import { writingURL } from 'constants/apis/url';
import { Meta, StoryObj } from '@storybook/react';
import { rest } from 'msw';
import {
  StoryContainer,
  StoryItemContainer,
  StoryItemContainerRow,
  StoryItemTitle,
} from 'styles/storybook';
import { GetWritingResponse } from 'types/apis/writings';
import WritingViewer from './WritingViewer';

const meta = {
  title: 'writing/WritingViewer',
  component: WritingViewer,
  args: {
    writingId: 200,
    categoryId: 1,
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
  render: ({ writingId, categoryId }) => {
    return (
      <>
        <StoryItemTitle>글 가져오기 성공</StoryItemTitle>
        <WritingViewer categoryId={categoryId} writingId={writingId}></WritingViewer>
      </>
    );
  },
};

export const Failure: Story = {
  render: () => {
    return (
      <>
        <>
          <StoryItemTitle>글 가져오기 실패</StoryItemTitle>
          <WritingViewer categoryId={1} writingId={404}></WritingViewer>
        </>
      </>
    );
  },
};
