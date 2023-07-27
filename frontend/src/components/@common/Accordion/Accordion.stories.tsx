import { Meta, StoryObj } from '@storybook/react';

import Accordion from './Accordion';
import { Size } from 'constants/components/common';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';

const ITEMS = [
  { header: '제목1', body: <p>내용</p> },
  { header: '제목2', body: <p>내용1 내용2</p> },
  {
    header: '제목3',
    body: [<p key={1}>내용1</p>, <p key={2}>내용2</p>, <p key={3}>내용3</p>],
  },
];

const headers = ITEMS.map((item) => item.header);
const bodies = ITEMS.map((item) => item.body);

const meta = {
  title: 'common/Accordion',
  args: {
    size: 'medium',
  },
  argTypes: {
    size: {
      description: '3가지 사이즈 입니다.',
      options: Object.values(Size),
      control: { type: 'radio' },
    },
  },
  component: Accordion,
} satisfies Meta<typeof Accordion>;

export default meta;
type Story = StoryObj<typeof meta>;

const AccordionValues = [
  { title: <p>제목1</p>, panel: Array.from({ length: 1 }, () => <p>내용</p>) },
  { title: <p>제목2</p>, panel: Array.from({ length: 5 }, () => <p>내용</p>) },
  { title: <p>제목3</p>, panel: Array.from({ length: 5 }, () => <button>내용</button>) },
  { title: <p>제목4</p>, panel: Array.from({ length: 5 }, () => <p>내용</p>) },
];

export const Playground: Story = {
  render: (args) => {
    return (
      <StoryContainer>
        <StoryItemContainer>
          <Accordion size={args.size}>
            {AccordionValues.map((value, index) => {
              return (
                <Accordion.Item key={index}>
                  <Accordion.Title>{value.title}</Accordion.Title>
                  <Accordion.Panel>{value.panel}</Accordion.Panel>
                </Accordion.Item>
              );
            })}
          </Accordion>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};

export const onClickPropsInTitle: Story = {
  render: () => {
    return (
      <StoryContainer>
        <StoryItemContainer>
          <Accordion>
            {AccordionValues.map((value, index) => {
              return (
                <Accordion.Item key={index}>
                  <Accordion.Title onClick={() => alert('긂 목록 API 요청')}>
                    {value.title}
                  </Accordion.Title>
                  <Accordion.Panel>{value.panel}</Accordion.Panel>
                </Accordion.Item>
              );
            })}
          </Accordion>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};

export const Sizes: Story = {
  render: () => {
    return (
      <StoryContainer>
        {Object.values(Size).map((size) => (
          <StoryItemContainer key={size}>
            <StoryItemTitle>{size}</StoryItemTitle>
            <Accordion size={size}>
              {AccordionValues.map((value, index) => {
                return (
                  <Accordion.Item key={index}>
                    <Accordion.Title>{value.title}</Accordion.Title>
                    <Accordion.Panel>{value.panel}</Accordion.Panel>
                  </Accordion.Item>
                );
              })}
            </Accordion>
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};
