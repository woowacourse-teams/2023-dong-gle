import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer } from 'styles/storybook';
import PublishingPropertySection from './PublishingPropertySection';

const meta: Meta<typeof PublishingPropertySection> = {
  title: 'publishing/PublishingPropertySection',
  component: PublishingPropertySection,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  render: () => (
    <StoryContainer>
      <PublishingPropertySection writingId={1} publishTo={'MEDIUM'} changeCurrentTab={() => {}} />
    </StoryContainer>
  ),
};
