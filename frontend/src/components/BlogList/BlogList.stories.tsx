import type { Meta, StoryObj } from '@storybook/react';
import BlogList from './BlogList';

const meta: Meta<typeof BlogList> = {
  component: BlogList,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof BlogList>;

export const Primary: Story = {
  render: () => <BlogList />,
};
