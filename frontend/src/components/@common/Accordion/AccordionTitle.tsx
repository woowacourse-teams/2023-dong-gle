import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';
import { ArrowRightIcon } from 'assets/icons';

type Props = {
  isOpen?: boolean;
  onClick?: () => void;
} & ComponentPropsWithoutRef<'button'>;

const AccordionTitle = ({ isOpen = false, onClick, children }: Props) => {
  return (
    <S.Button onClick={onClick}>
      <S.Container isOpen={isOpen}>
        <ArrowRightIcon width={8} height={14} />
        {children}
      </S.Container>
    </S.Button>
  );
};

export default AccordionTitle;

const S = {
  Button: styled.button`
    width: 100%;
    padding: 1.2rem;

    border-radius: 4px;
  `,

  Container: styled.div<Record<'isOpen', boolean>>`
    display: flex;
    gap: 0.8rem;
    width: 100%;

    & > svg {
      rotate: ${({ isOpen }) => isOpen && '90deg'};
      transition: rotate 0.2s;
    }
  `,
};
