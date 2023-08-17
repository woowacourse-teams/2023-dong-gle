import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer } from 'styles/storybook';
import WritingSideBar from './WritingSideBar';

const meta: Meta<typeof WritingSideBar> = {
  title: 'publishing/WritingSideBar',
  component: WritingSideBar,
  args: {},
  argTypes: {},
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Primary: Story = {
  render: () => {
    return (
      <StoryContainer>
        <WritingSideBar />
      </StoryContainer>
    );
  },
};
