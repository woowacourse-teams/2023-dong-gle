import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer } from 'styles/storybook';
import MediumPublishingPropertySection from './MediumPublishingPropertySection';

const meta: Meta<typeof MediumPublishingPropertySection> = {
  title: 'publishing/PublishingPropertySection',
  component: MediumPublishingPropertySection,
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  render: () => (
    <StoryContainer>
      <MediumPublishingPropertySection
        writingId={1}
        publishTo={'MEDIUM'}
        selectCurrentTab={() => {}}
      />
    </StoryContainer>
  ),
};
