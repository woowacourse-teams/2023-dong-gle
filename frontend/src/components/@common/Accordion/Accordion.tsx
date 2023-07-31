import { ComponentPropsWithoutRef } from 'react';
import { RuleSet, css, styled } from 'styled-components';
import { Size } from 'types/components/common';
import AccordionItem from './AccordionItem';
import AccordionTitle from './AccordionTitle';
import AccordionPanel from './AccordionPanel';

type Props = {
  size?: Size;
} & ComponentPropsWithoutRef<'ul'>;

const Accordion = ({ size = 'medium', children, ...rest }: Props) => {
  return (
    <S.List size={size} {...rest}>
      {children}
    </S.List>
  );
};

export default Accordion;

Accordion.Item = AccordionItem;
Accordion.Title = AccordionTitle;
Accordion.Panel = AccordionPanel;

const generateAccordionSize = (size: Required<Props>['size']): RuleSet<object> => {
  const styles: Record<typeof size, ReturnType<typeof generateAccordionSize>> = {
    small: css`
      width: 12rem;

      * {
        font-size: 1.2rem;
      }
    `,
    medium: css`
      width: 24rem;

      * {
        font-size: 1.6rem;
      }
    `,
    large: css`
      width: 36rem;

      * {
        font-size: 2rem;
      }
    `,
  };

  return styles[size];
};

const S = {
  List: styled.ul<Pick<Props, 'size'>>`
    ${({ size = 'medium' }) => generateAccordionSize(size)};

    display: flex;
    flex-direction: column;
    gap: 1.2rem;
  `,
};
