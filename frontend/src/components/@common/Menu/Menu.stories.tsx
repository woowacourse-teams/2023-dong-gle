import { Meta, StoryObj } from '@storybook/react';

import Menu from './Menu';
import { StoryContainer, StoryItemContainer } from 'styles/storybook';

const meta = {
  title: 'common/Menu',
  component: Menu,
} satisfies Meta<typeof Menu>;

export default meta;
type Story = StoryObj<typeof meta>;

const MenuValues = [
  { title: '도움말', handleMenuItemClick: () => {} },
  { title: '도움말도움말도움말도움말도움말도움말도움말도움말', handleMenuItemClick: () => {} },
  { title: '도움말', handleMenuItemClick: () => {} },
  { title: '도움말', handleMenuItemClick: () => {} },
];

export const Playground: Story = {
  render: (args) => {
    return (
      <StoryContainer>
        <StoryItemContainer>
          <Menu>
            {MenuValues.map(({ title, handleMenuItemClick }) => {
              return (
                <Menu.Item key={title} title={title} handleMenuItemClick={handleMenuItemClick} />
              );
            })}
          </Menu>
        </StoryItemContainer>
      </StoryContainer>
    );
  },
};
