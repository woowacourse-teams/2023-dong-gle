import { BLOG } from 'constants/blog';
import type { Meta, StoryObj } from '@storybook/react';
import BlogButton from './BlogButton';

const meta: Meta<typeof BlogButton> = {
  component: BlogButton,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof BlogButton>;

export const Primary: Story = {
  render: () => <BlogButton blogName={BLOG.MEDIUM} />,
};
