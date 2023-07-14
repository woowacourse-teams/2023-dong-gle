import type { Meta, StoryObj } from '@storybook/react';
import PublishingSection from './PublishingSection';

const meta: Meta<typeof PublishingSection> = {
  component: PublishingSection,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof PublishingSection>;

export const Primary: Story = {
  render: () => <PublishingSection />,
};
