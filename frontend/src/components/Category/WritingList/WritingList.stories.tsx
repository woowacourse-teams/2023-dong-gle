import type { Meta, StoryObj } from '@storybook/react';
import WritingList from './WritingList';

const meta: Meta<typeof WritingList> = {
  title: 'WritingList',
  component: WritingList,
  args: {
    categoryId: 1,
    selectedCategoryId: 1,
  },
  argTypes: {
    categoryId: {
      description: '현재 속한 카테고리의 아이디입니다.',
    },
    selectedCategoryId: {
      description: '클릭된 카테고리의 아이디입니다.',
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};
