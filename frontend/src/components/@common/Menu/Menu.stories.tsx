import { Meta, StoryObj } from '@storybook/react';

import Menu from './Menu';
import { StoryContainer, StoryItemContainer } from 'styles/storybook';

const meta = {
  title: 'common/Menu',
  args: {
    initialIsOpen: true,
    verticalDirection: 'down',
    horizonDirection: 'left',
  },
  argTypes: {
    initialIsOpen: {
      description: '메뉴의 초기 열림/닫힘 상태입니다.',
    },
    verticalDirection: {
      description: '메뉴가 수직을 기준으로 렌더링 되는 위치입니다.',
    },
    horizonDirection: {
      description: '메뉴가 수평을 기준으로 렌더링 되는 위치입니다.',
    },
  },
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
          <Menu verticalDirection={args.verticalDirection} horizonDirection={args.horizonDirection}>
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
