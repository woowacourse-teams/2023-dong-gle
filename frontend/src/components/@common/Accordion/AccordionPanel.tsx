import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';

type Props = {
  isOpen?: boolean;
} & ComponentPropsWithoutRef<'div'>;

const AccordionPanel = ({ isOpen = false, children }: Props) => {
  if (!isOpen) return null;

  return <S.Wrapper>{children}</S.Wrapper>;
};

export default AccordionPanel;

const S = {
  Wrapper: styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    padding: 0.8rem 1.2rem 0 4rem;
  `,
};
