import { ComponentPropsWithoutRef, ReactNode } from 'react';
import { styled } from 'styled-components';

type Props = {
  panel: ReactNode;
  isOpen: boolean;
  onPanelClick?: () => void;
} & ComponentPropsWithoutRef<'div'>;

const AccordionPanel = ({ panel, isOpen, onPanelClick, ...rest }: Props) => {
  if (!isOpen) return null;

  return (
    <S.Wrapper onClick={onPanelClick} {...rest}>
      {panel}
    </S.Wrapper>
  );
};

export default AccordionPanel;

const S = {
  Wrapper: styled.div<Pick<Props, 'onPanelClick'>>`
    display: flex;
    flex-direction: column;
    width: 100%;
    padding: 0.8rem 1.2rem 0 3rem;
    cursor: ${({ onPanelClick }) => onPanelClick && 'pointer'};
  `,
};
