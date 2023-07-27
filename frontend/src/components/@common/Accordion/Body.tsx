import { ComponentPropsWithoutRef, ReactNode } from 'react';
import { styled } from 'styled-components';

type Props = {
  body: ReactNode;
  isOpen: boolean;
} & ComponentPropsWithoutRef<'div'>;

const Body = ({ body, isOpen, ...rest }: Props) => {
  if (!isOpen) return null;

  return <S.Wrapper {...rest}>{body}</S.Wrapper>;
};

export default Body;

const S = {
  Wrapper: styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    padding: 0.8rem 1.2rem 0 3rem;
  `,
};
