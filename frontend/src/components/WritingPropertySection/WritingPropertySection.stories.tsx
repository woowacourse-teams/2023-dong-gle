import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer } from 'styles/storybook';
import WritingPropertySection from './WritingPropertySection';

const meta: Meta<typeof WritingPropertySection> = {
  title: 'publishing/WritingPropertySection',
  component: WritingPropertySection,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  render: () => (
    <StoryContainer>
      <WritingPropertySection writingId={200} />
    </StoryContainer>
  ),
};
