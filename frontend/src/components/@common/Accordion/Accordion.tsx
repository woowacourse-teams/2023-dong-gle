import { ComponentPropsWithoutRef, ReactNode } from 'react';
import { RuleSet, css, styled } from 'styled-components';
import Item from './Item';
import { TextAlign, Size, Variant } from 'types/components/common';

type Props = {
  headers: string[];
  bodies: ReactNode[];
  variant?: Variant;
  size?: Size;
  textAlign?: TextAlign;
} & ComponentPropsWithoutRef<'ul'>;

const Accordion = ({
  headers,
  bodies,
  variant = 'primary',
  size = 'medium',
  textAlign = 'start',
  ...rest
}: Props) => {
  if (headers.length !== bodies.length) return null;

  const accordionValues = headers.map((header, index) => [header, bodies[index]]);

  return (
    <S.List variant={variant} size={size} textAlign={textAlign} {...rest}>
      {accordionValues.map(([header, body]) => {
        return <Item key={`${header}`} header={header} body={body} />;
      })}
    </S.List>
  );
};

export default Accordion;

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
