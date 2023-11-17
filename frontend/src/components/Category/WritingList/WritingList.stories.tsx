import type { Meta, StoryObj } from '@storybook/react';
import WritingList from './WritingList';

const meta: Meta<typeof WritingList> = {
  title: 'writing/WritingList',
  component: WritingList,
  args: {
    categoryId: 1,
    isOpen: true,
  },
  argTypes: {
    categoryId: {
      description: '현재 속한 카테고리의 아이디입니다.',
    },
    isOpen: {
      description: '카테고리 토글의 열림 여부 입니다.',
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};
