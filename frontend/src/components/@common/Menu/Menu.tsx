import { PropsWithChildren } from 'react';
import styled from 'styled-components';
import Item from './Item';
import { useMenu } from './useMenu';

type VerticalDirection = 'up' | 'down';
type HorizonDirection = 'left' | 'right';

type Props = {
  initialIsOpen?: boolean;
  verticalDirection?: VerticalDirection;
  horizonDirection?: HorizonDirection;
} & PropsWithChildren;

const Menu = ({
  initialIsOpen = false,
  verticalDirection = 'down',
  horizonDirection = 'left',
  children,
}: Props) => {
  const { menuRef, isOpen, toggleIsOpen } = useMenu(initialIsOpen);

  return (
    <S.Menu ref={menuRef} onClick={toggleIsOpen}>
      {isOpen ? (
        <S.MenuList $verticalDirection={verticalDirection} $horizonDirection={horizonDirection}>
          {children}
        </S.MenuList>
      ) : null}
    </S.Menu>
  );
};

Menu.Item = Item;

export default Menu;

const S = {
  Menu: styled.div`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
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
    	bottom: 100%;
  	`}
    ${({ $verticalDirection }) =>
      $verticalDirection === 'down' &&
      `
			top: 100%;
  	`}
    ${({ $horizonDirection }) =>
      $horizonDirection === 'right' &&
      `
    	left: 0;
  	`}
		${({ $horizonDirection }) =>
      $horizonDirection === 'left' &&
      `
    	right: 0;
  	`}
    width: fit-content;

    border: 1px solid ${({ theme }) => theme.color.gray4};
    border-radius: 4px;
    background-color: ${({ theme }) => theme.color.gray1};
    box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.1);

    z-index: 1;
  `,
};
