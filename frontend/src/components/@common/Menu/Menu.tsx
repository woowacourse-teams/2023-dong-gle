import { PropsWithChildren, useState } from 'react';
import styled from 'styled-components';
import Item from './Item';

type Direction = 'up' | 'down';

type Props = {
  isOpen: boolean;
  closeMenu: () => void;
  direction?: Direction;
} & PropsWithChildren;

const Menu = ({ isOpen, closeMenu, direction = 'down', children }: Props) => {
  if (!isOpen) return null;

  return (
    <S.Menu>
      <S.Backdrop onClick={closeMenu} />
      <S.MenuList $direction={direction}>{children}</S.MenuList>
    </S.Menu>
  );
};

Menu.Item = Item;

export default Menu;

const S = {
  Menu: styled.div``,

  Backdrop: styled.button`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 100vw;
    height: 100vh;

    cursor: default;
    opacity: 0.1;
  `,

  MenuList: styled.ul<{ $direction: Direction }>`
    display: flex;
    flex-direction: column;
    position: absolute;
    ${({ $direction }) =>
      $direction === 'up' &&
      `
    bottom: 80%;
  	`}
    left: 50%;

    border: 2px solid ${({ theme }) => theme.color.gray4};
    background-color: ${({ theme }) => theme.color.gray1};
  `,
};
