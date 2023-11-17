import type { Meta, StoryObj } from '@storybook/react';
import Section from './Section';

const meta: Meta<typeof Section> = {
  title: 'category/Section',
  component: Section,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {};
