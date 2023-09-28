import { PropsWithChildren, forwardRef } from 'react';
import styled from 'styled-components';
import Item from './Item';

type VerticalDirection = 'up' | 'down';
type HorizonDirection = 'left' | 'right';

type Props = {
  isOpen: boolean;
  verticalDirection?: VerticalDirection;
  horizonDirection?: HorizonDirection;
} & PropsWithChildren;

const Menu = ({
  isOpen,
  verticalDirection = 'down',
  horizonDirection = 'left',
  children,
}: Props) => {
  if (!isOpen) return null;

  return (
    <S.Menu>
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
    width: 20rem;

    border: 1px solid ${({ theme }) => theme.color.gray4};
    background-color: ${({ theme }) => theme.color.gray1};
    box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.1);
  `,
};
