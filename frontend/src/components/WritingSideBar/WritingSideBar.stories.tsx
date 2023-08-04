import type { Meta, StoryObj } from '@storybook/react';
import { StoryContainer } from 'styles/storybook';
import WritingSideBar from './WritingSideBar';

const meta: Meta<typeof WritingSideBar> = {
  title: 'publishing/WritingSideBar',
  component: WritingSideBar,
  args: {
    writingId: 200,
  },
  argTypes: {
    writingId: {
      description: '글의 id값입니다.',
      control: { type: 'number' },
    },
  },
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
