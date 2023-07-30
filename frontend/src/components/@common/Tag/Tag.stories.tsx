/* eslint-disable react/jsx-key */
import { Meta, StoryObj } from '@storybook/react';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';
import Tag from './Tag';

const meta = {
  title: 'common/Tag',
  component: Tag,
  args: {
    removable: true,
    children: '태그',
  },
  argTypes: {
    removable: {
      description: '삭제 가능한 태그인지 여부입니다.',
      control: { type: 'boolean' },
    },
    children: {
      description: '태그 내부 content에 해당합니다.',
      control: { type: 'text' },
    },
  },
} satisfies Meta<typeof Tag>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {};

export const Removable: Story = {
  render: () => {
    return (
      <StoryContainer>
        <StoryItemContainer>
          <StoryItemTitle>true</StoryItemTitle>
          <Tag removable>삭제가능</Tag>
        </StoryItemContainer>
        <StoryItemContainer>
          <StoryItemTitle>false</StoryItemTitle>
          <Tag removable={false}>삭제불가</Tag>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
