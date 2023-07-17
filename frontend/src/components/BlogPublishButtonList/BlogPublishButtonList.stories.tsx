import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer } from 'styles/storybook';
import BlogPublishButtonList from './BlogPublishButtonList';

const meta: Meta<typeof BlogPublishButtonList> = {
  title: 'publishing/BlogPublishButtonList',
  component: BlogPublishButtonList,
  args: {
    writingId: 1,
  },
  argTypes: {
    writingId: {
      description: '글의 id값입니다.',
      control: { type: 'number' },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  render: ({ writingId }) => {
    return (
      <StoryContainer>
        <BlogPublishButtonList writingId={writingId} />
      </StoryContainer>
    );
  },
};
