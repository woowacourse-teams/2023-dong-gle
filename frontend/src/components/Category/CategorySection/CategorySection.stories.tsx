import type { Meta, StoryObj } from '@storybook/react';
import CategorySection from './CategorySection';

const meta: Meta<typeof CategorySection> = {
  title: 'CategorySection',
  component: CategorySection,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};
