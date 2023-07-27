import { ComponentPropsWithoutRef, ReactNode } from 'react';
import { styled } from 'styled-components';
import { ArrowRightIcon } from 'assets/icons';

type Props = {
  accordionTitle: ReactNode;
  isOpen: boolean;
} & ComponentPropsWithoutRef<'button'>;

const AccordionTitle = ({ accordionTitle, isOpen, ...rest }: Props) => {
  return (
    <S.Button {...rest}>
      <S.Container isOpen={isOpen}>
        <ArrowRightIcon width={8} height={14} />
        {accordionTitle}
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

  Container: styled.div<Pick<Props, 'isOpen'>>`
    display: flex;
    gap: 0.8rem;
    width: 100%;

    & > svg {
      rotate: ${({ isOpen }) => isOpen && '90deg'};
      transition: rotate 0.2s;
    }
  `,
};
