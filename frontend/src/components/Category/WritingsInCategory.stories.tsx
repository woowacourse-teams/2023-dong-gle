import type { Meta, StoryObj } from '@storybook/react';
import WritingsInCategory from './WritingsInCategory';
import { StoryContainer, StoryItemContainer, StoryItemTitle } from 'styles/storybook';

const meta: Meta<typeof WritingsInCategory> = {
  title: 'WritingsInCategory',
  component: WritingsInCategory,
  args: {
    writings: [
      { id: 1, title: '동글' },
      { id: 2, title: '테트리스 중독' },
      {
        id: 3,
        title:
          '테트리스 하고 싶다 테트리스 하고 싶다 테트리스 하고 싶다 테트리스 하고 싶다 테트리스 하고 싶다 테트리스 하고 싶다',
      },
    ],
  },
  argTypes: {
    writings: {
      description: '글 목록 데이터입니다.',
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};
