import Menu from 'components/@common/Menu/Menu';
import useOutsideClickEffect from 'hooks/@common/useOutsideClickEffect';
import { useRef, useState } from 'react';
import styled from 'styled-components';

const HelpMenu = () => {
  const [isOpen, setIsOpen] = useState(false);
  const menuRef = useRef<HTMLDivElement>(null);

  const closeMenu = () => setIsOpen(false);
  useOutsideClickEffect(menuRef, closeMenu);

  const helpMenus = [
    {
      title: '사용법',
      handleMenuItemClick: () =>
        window.open(
          'https://github.com/woowacourse-teams/2023-dong-gle/wiki/%EB%8F%99%EA%B8%80-%EB%8F%84%EC%9B%80%EB%A7%90',
        ),
    },
    {
      title: '피드백',
      handleMenuItemClick: () => window.open('https://forms.gle/wSjCQKb4jhmFwSWQ9'),
    },
  ];

  return (
    <div ref={menuRef}>
      <S.HelpMenu onClick={() => setIsOpen(!isOpen)}>
        ?
        <Menu isOpen={isOpen} verticalDirection='up'>
          {helpMenus.map(({ title, handleMenuItemClick }) => {
            return (
              <Menu.Item key={title} title={title} handleMenuItemClick={handleMenuItemClick} />
            );
          })}
        </Menu>
      </S.HelpMenu>
    </div>
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
    box-shadow: 0px 0px 4px rgba(0, 0, 0, 0.1);
  `,
};
