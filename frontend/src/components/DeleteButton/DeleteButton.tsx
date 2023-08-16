import { TrashCanIcon } from 'assets/icons';
import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';

const DeleteButton = ({ ...rest }: ComponentPropsWithoutRef<'button'>) => {
  return (
    <S.Button {...rest}>
      <TrashCanIcon width={12} height={12} aria-label='휴지통 아이콘' />
    </S.Button>
  );
};

export default DeleteButton;

const S = {
  Button: styled.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
};
