import { ComponentPropsWithoutRef, ReactNode } from 'react';
import { RuleSet, css, styled } from 'styled-components';
import { TextAlign, Size, Variant } from 'types/components/common';
import AccordionItem from './AccordionItem';
import AccordionTitle from './AccordionTitle';
import AccordionPanel from './AccordionPanel';

export type AccordionTitle = ReactNode;
export type Panel = ReactNode;

type Props = {
  variant?: Variant;
  size?: Size;
  textAlign?: TextAlign;
} & ComponentPropsWithoutRef<'ul'>;

const Accordion = ({
  variant = 'primary',
  size = 'medium',
  textAlign = 'start',
  children,
  ...rest
}: Props) => {
  return (
    <S.List variant={variant} size={size} textAlign={textAlign} {...rest}>
      {children}
    </S.List>
  );
};

export default Accordion;

Accordion.Item = AccordionItem;
Accordion.Title = AccordionTitle;
Accordion.Panel = AccordionPanel;

const generateVariantStyle = (variant: Required<Props>['variant']): RuleSet<object> => {
  const styles: Record<typeof variant, ReturnType<typeof generateVariantStyle>> = {
    primary: css`
      ${({ theme }) => css`
        & > li > button {
          color: ${theme.color.gray10};
          background-color: ${theme.color.gray1};
        }

        & > li > button:hover {
          background-color: ${theme.color.gray3};
        }
      `}
    `,
    dark: css`
      ${({ theme }) => css`
        & > li > button {
          color: ${theme.color.gray1};
          background-color: ${theme.color.gray10};
        }

        & > li > button:hover {
          background-color: ${theme.color.gray8};
        }
      `}
    `,
  };
  return styles[variant];
};

const generateAccordionSize = (size: Required<Props>['size']): RuleSet<object> => {
  const styles: Record<typeof size, ReturnType<typeof generateAccordionSize>> = {
    small: css`
      width: 12rem;
    `,
    medium: css`
      width: 24rem;
    `,
    large: css`
      width: 36rem;
    `,
  };

  return styles[size];
};

const S = {
  List: styled.ul<Pick<Props, 'variant' | 'size' | 'textAlign'>>`
    ${({ size = 'medium' }) => generateAccordionSize(size)};
    ${({ variant = 'primary' }) => generateVariantStyle(variant)};

    display: flex;
    flex-direction: column;
    gap: 1.2rem;

    & * {
      text-align: ${({ textAlign }) => textAlign};
    }
  `,
};
