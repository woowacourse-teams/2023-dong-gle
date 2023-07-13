/* eslint-disable react/jsx-key */
import { Meta, StoryObj } from '@storybook/react';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';

import Button, { Variant, Size, Align } from './Button';

const meta = {
  title: 'common/Button',
  component: Button,
  args: {
    variant: 'primary',
    size: 'medium',
    block: false,
    align: 'center',
    children: 'Button',
  },
  argTypes: {
    variant: {
      description: '미리 정의해놓은 버튼의 스타일입니다.',
      options: Object.values(Variant),
      control: { type: 'radio' },
    },
    size: {
      description: '크기에 따라 padding과 font-size가 바뀝니다.',
      options: Object.values(Size),
      control: { type: 'radio' },
    },
    block: {
      description: '부모의 width를 따라갑니다.',
    },
    align: {
      description: '버튼 내부 글자의 정렬을 결정합니다.',
      options: Object.values(Align),
      control: { type: 'radio' },
    },
    children: {
      description: '버튼 내부 content에 해당합니다.',
      control: { type: 'text' },
    },
  },
} satisfies Meta<typeof Button>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Variants: Story = {
  render: ({ children }) => {
    return (
      <StoryContainer>
        {Object.values(Variant).map((variant) => (
          <StoryItemContainer>
            <StoryItemTitle>{variant}</StoryItemTitle>
            <Button variant={variant}>{children}</Button>
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const Sizes: Story = {
  render: ({ children }) => {
    return (
      <StoryContainer>
        {Object.values(Size).map((size) => (
          <StoryItemContainer>
            <StoryItemTitle>{size}</StoryItemTitle>
            <Button size={size}>{children}</Button>
          </StoryItemContainer>
        ))}
      </StoryContainer>
    );
  },
};

export const Block: Story = {
  render: ({ children }) => {
    return (
      <StoryContainer>
        <StoryItemContainer style={{ width: '400px' }}>
          <StoryItemTitle>Block</StoryItemTitle>
          <div style={{ width: '100%' }}>
            <Button block>{children}</Button>
          </div>
          <StoryItemTitle>Block ❌</StoryItemTitle>
          <div style={{ width: '100%' }}>
            <Button>{children}</Button>
          </div>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};

export const Aligns: Story = {
  render: () => {
    return (
      <StoryContainer>
        <StoryItemContainer style={{ width: '400px' }}>
          <StoryItemTitle>left</StoryItemTitle>
          <Button align='left'>left</Button>
        </StoryItemContainer>
        <StoryItemContainer style={{ width: '400px' }}>
          <StoryItemTitle>center</StoryItemTitle>
          <Button align='center'>center</Button>
        </StoryItemContainer>
        <StoryItemContainer style={{ width: '400px' }}>
          <StoryItemTitle>right</StoryItemTitle>
          <Button align='right'>right</Button>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};

export const Disabled: Story = {
  render: ({ children }) => {
    return (
      <StoryContainer>
        <StoryItemContainer style={{ width: '400px' }}>
          <StoryItemTitle>Disabled</StoryItemTitle>
          <div style={{ width: '100%' }}>
            <Button disabled>{children}</Button>
          </div>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
