import type { Meta, StoryObj } from '@storybook/react';
import BlogPublishButtonItem from './BlogPublishButtonItem';
import { StoryContainer } from 'styles/storybook';

const meta: Meta<typeof BlogPublishButtonItem> = {
  title: 'publishing/BlogPublishButtonItem',
  component: BlogPublishButtonItem,
  args: {
    name: 'MEDIUM',
    writingId: 1,
    isPublished: false,
  },
  argTypes: {
    name: {
      description: '블로그 이름입니다.',
      control: { type: 'text' },
    },
    writingId: {
      description: '글의 id값입니다.',
      control: { type: 'number' },
    },
    isPublished: {
      description: '글의 배포 여부입니다.',
      control: { type: 'boolean' },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  render: ({ name, writingId, isPublished }) => {
    return (
      <StoryContainer>
        <BlogPublishButtonItem name={name} writingId={writingId} isPublished={isPublished} />
      </StoryContainer>
    );
  },
};

export const Published: Story = {
  render: ({ name, writingId }) => {
    return (
      <StoryContainer>
        <BlogPublishButtonItem name={name} writingId={writingId} isPublished={true} />
      </StoryContainer>
    );
  },
};
