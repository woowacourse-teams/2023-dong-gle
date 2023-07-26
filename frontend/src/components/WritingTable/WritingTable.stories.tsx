import { Meta, StoryObj } from '@storybook/react';
import WritingTable from './WritingTable';
import {
  StoryContainer,
  StoryItemContainer,
  StoryItemContainerRow,
  StoryItemTitle,
} from 'styles/storybook';

const meta = {
  title: 'WritingTable',
  component: WritingTable,
  args: {
    categoryId: 1,
  },
  // argTypes: {},
} satisfies Meta<typeof WritingTable>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: ({ categoryId }) => {
    return (
      <StoryContainer>
        <StoryItemContainer style={{ width: '800px' }}>
          <StoryItemTitle>기본</StoryItemTitle>
          <WritingTable categoryId={categoryId} />
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
