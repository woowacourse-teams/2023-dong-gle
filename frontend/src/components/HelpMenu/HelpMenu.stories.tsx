import { Meta, StoryObj } from '@storybook/react';

import HelpMenu from './HelpMenu';
import { StoryContainer, StoryItemContainer } from 'styles/storybook';

const meta = {
  title: 'common/HelpMenu',
  component: HelpMenu,
} satisfies Meta<typeof HelpMenu>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Playground: Story = {
  render: (args) => {
    return (
      <StoryContainer>
        <StoryItemContainer>
          <HelpMenu />
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
