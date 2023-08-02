import type { Meta, StoryObj } from '@storybook/react';
import PublishingSection from './PublishingSection';
import { StoryContainer } from 'styles/storybook';

const meta: Meta<typeof PublishingSection> = {
  title: 'publishing/PublishingSection',
  component: PublishingSection,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  render: ({ onBlogButtonClick, onTabClick }) => {
    return (
      <StoryContainer>
        <PublishingSection onTabClick={onTabClick} onBlogButtonClick={onBlogButtonClick} />
      </StoryContainer>
    );
  },
};
