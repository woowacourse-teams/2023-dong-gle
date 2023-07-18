import type { Meta, StoryObj } from '@storybook/react';
import PublishingSection from './PublishingSection';
import { StoryContainer } from 'styles/storybook';

const meta: Meta<typeof PublishingSection> = {
  title: 'publishing/PublishingSection',
  component: PublishingSection,
  args: {
    writingId: 1,
    isPublished: true,
  },
  argTypes: {
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
  render: ({ writingId, isPublished }) => {
    return (
      <StoryContainer>
        <PublishingSection writingId={writingId} isPublished={isPublished} />
      </StoryContainer>
    );
  },
};
