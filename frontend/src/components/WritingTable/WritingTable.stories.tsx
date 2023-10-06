import { Meta, StoryObj } from '@storybook/react';
import WritingTable from './WritingTable';
import {
  StoryContainer,
  StoryItemContainer,
  StoryItemContainerRow,
  StoryItemTitle,
} from 'styles/storybook';
import { writingTable } from 'mocks/data/writingTablePage';

const meta = {
  title: 'WritingTable',
  component: WritingTable,
  args: {
    writings: writingTable.writings,
    categoryId: 1,
  },
  // argTypes: {},
} satisfies Meta<typeof WritingTable>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: ({ writings, categoryId }) => {
    return (
      <StoryContainer>
        <StoryItemContainer style={{ width: '800px' }}>
          <StoryItemTitle>기본</StoryItemTitle>
          <WritingTable categoryId={categoryId} writings={writings} />
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
