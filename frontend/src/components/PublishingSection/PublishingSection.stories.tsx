import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer } from 'styles/storybook';
import PublishingSection from './PublishingSection';

const meta: Meta<typeof PublishingSection> = {
  title: 'publishing/PublishingSection',
  component: PublishingSection,
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
        <PublishingSection writingId={writingId} />
      </StoryContainer>
    );
  },
};
