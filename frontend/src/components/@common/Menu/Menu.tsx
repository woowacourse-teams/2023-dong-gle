import { PropsWithChildren } from 'react';
import styled from 'styled-components';
import Item from './Item';

type VerticalDirection = 'up' | 'down';
type HorizonDirection = 'left' | 'right';

type Props = {
  isOpen: boolean;
  closeMenu: () => void;
  verticalDirection?: VerticalDirection;
  horizonDirection?: HorizonDirection;
} & PropsWithChildren;

const Menu = ({
  isOpen,
  closeMenu,
  verticalDirection = 'down',
  horizonDirection = 'left',
  children,
}: Props) => {
  if (!isOpen) return null;

  return (
    <S.Menu>
      <S.Backdrop onClick={closeMenu} />
      <S.MenuList $verticalDirection={verticalDirection} $horizonDirection={horizonDirection}>
        {children}
      </S.MenuList>
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

  MenuList: styled.ul<{
    $verticalDirection: VerticalDirection;
    $horizonDirection: HorizonDirection;
  }>`
    display: flex;
    flex-direction: column;
    position: absolute;
    ${({ $verticalDirection }) =>
      $verticalDirection === 'up' &&
      `
    	bottom: 80%;
  	`}
    ${({ $horizonDirection }) =>
      $horizonDirection === 'right' &&
      `
    	left: 50%;
  	`}

    right: 50%;

    border: 2px solid ${({ theme }) => theme.color.gray4};
    background-color: ${({ theme }) => theme.color.gray1};
  `,
};
