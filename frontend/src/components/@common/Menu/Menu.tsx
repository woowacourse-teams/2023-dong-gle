import { PropsWithChildren } from 'react';
import styled from 'styled-components';
import Item from './Item';

type Direction = 'up' | 'down';

type Props = {
  direction?: Direction;
} & PropsWithChildren;

const Menu = ({ direction = 'down', children }: Props) => {
  return (
    <S.Menu>
      <S.Backdrop />
      <S.MenuList $direction={direction}>{children}</S.MenuList>
    </S.Menu>
  );
};

Menu.Item = Item;

export default Menu;

const S = {
  Menu: styled.div``,

  Backdrop: styled.div`
    position: absolute;
    width: 100vw;
    height: 100vh;
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
