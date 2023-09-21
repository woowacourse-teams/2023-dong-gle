import Menu from 'components/@common/Menu/Menu';
import { useState } from 'react';
import styled from 'styled-components';

const HelpMenu = () => {
  const [isOpen, setIsOpen] = useState(false);
  const helpMenus = [
    {
      title: '동글 위키보러 가기',
      handleMenuItemClick: () =>
        window.open(
          'https://github.com/woowacourse-teams/2023-dong-gle/wiki/%EB%8F%99%EA%B8%80-%EB%8F%84%EC%9B%80%EB%A7%90',
        ),
    },
  ];

  const closeMenu = () => setIsOpen(false);

  return (
    <S.HelpMenu onClick={() => setIsOpen(!isOpen)}>
      ?
      <Menu isOpen={isOpen} closeMenu={closeMenu} verticalDirection='up'>
        {helpMenus.map(({ title, handleMenuItemClick }) => {
          return <Menu.Item key={title} title={title} handleMenuItemClick={handleMenuItemClick} />;
        })}
      </Menu>
    </S.HelpMenu>
  );
};

export default HelpMenu;

const S = {
  HelpMenu: styled.button`
    position: absolute;
    bottom: 20px;
    right: 20px;
    width: 40px;
    height: 40px;
    border: 1px solid ${({ theme }) => theme.color.gray5};
    border-radius: 50%;
    font-size: x-large;
  `,
};
