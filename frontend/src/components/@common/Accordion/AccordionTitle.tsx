import { ComponentPropsWithoutRef, MouseEvent } from 'react';
import { styled } from 'styled-components';
import { ArrowRightIcon } from 'assets/icons';

type Props = {
  isOpen?: boolean;
  onClickIcon?: () => void;
  onClick?: () => void;
} & ComponentPropsWithoutRef<'div'>;

const AccordionTitle = ({ isOpen = false, onClickIcon, onClick, children }: Props) => {
  const togglePanel = (e: MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();

    if (onClickIcon) onClickIcon();

    if (onClick) onClick();
  };

  return (
    <S.Container>
      <S.IconButton isOpen={isOpen} onClick={togglePanel}>
        <ArrowRightIcon width={8} height={14} />
      </S.IconButton>
      {children}
    </S.Container>
  );
};

export default AccordionTitle;

const S = {
  Container: styled.div`
    display: flex;
    align-items: center;
    gap: 0.8rem;
    width: 100%;
    padding: 1.2rem;
    border-radius: 4px;
  `,

  IconButton: styled.button<Record<'isOpen', boolean>>`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 1.8rem;
    height: 2.2rem;
    padding: 0.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }

    & > svg {
      rotate: ${({ isOpen }) => isOpen && '90deg'};
      transition: rotate 0.2s;
    }
  `,
};
