/* eslint-disable react/jsx-key */
import { Meta, StoryObj } from '@storybook/react';
import TagInput from './TagInput';

const meta = {
  title: 'common/TagInput',
  component: TagInput,
} satisfies Meta<typeof TagInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {};
