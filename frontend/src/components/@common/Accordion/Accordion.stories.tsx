import { Meta, StoryObj } from '@storybook/react';

import Accordion from './Accordion';
import { Size, TextAlign, Variant } from 'constants/components/common';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';
import { ReactNode } from 'react';

const accordionContents = [
  [<StoryItemTitle>제목</StoryItemTitle>, <p>내용</p>],
  [<StoryItemTitle>제목</StoryItemTitle>, <p>내용1 내용2</p>],
  [
    <StoryItemTitle>제목</StoryItemTitle>,
    <div>
      <p key={1}>내용1</p>
      <p key={2}>내용2</p>
      <p key={3}>내용3</p>
    </div>,
  ],
] as [ReactNode, ReactNode][];

const meta = {
  title: 'common/Accordion',
  args: {
    accordionContents: accordionContents,
    variant: 'primary',
    size: 'medium',
    textAlign: 'start',
  },
  argTypes: {
    accordionContents: {
      description: '제목과 내용으로 이루어진 튜플 배열입니다.',
      control: { type: 'text' },
    },
    variant: {
      description: '정의된 스타일입니다.',
      options: Object.values(Variant),
      control: { type: 'radio' },
    },
    size: {
      description: '3가지 사이즈 입니다.',
      options: Object.values(Size),
      control: { type: 'radio' },
    },
    textAlign: {
      description: '글자 정렬 방식입니다. 제목은 정렬하지 않습니다.',
      options: Object.values(TextAlign),
      control: { type: 'radio' },
    },
  },
  component: Accordion,
} satisfies Meta<typeof Accordion>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Variants: Story = {
  render: () => {
    return (
      <StoryContainer>
        {Object.values(Variant).map((variant) => (
          <StoryItemContainer key={variant}>
            <StoryItemTitle>{variant}</StoryItemTitle>
            <Accordion accordionContents={accordionContents} variant={variant} />
          </StoryItemContainer>
        ))}
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
            <Accordion accordionContents={accordionContents} variant='dark' size={size} />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const TextAligns: Story = {
  render: () => {
    return (
      <StoryContainer>
        {Object.values(TextAlign).map((textAlign) => (
          <StoryItemContainer key={textAlign}>
            <StoryItemTitle>{textAlign}</StoryItemTitle>
            <Accordion accordionContents={accordionContents} textAlign={textAlign} />
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};
