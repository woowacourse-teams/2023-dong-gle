import { ComponentPropsWithRef, ForwardedRef, forwardRef } from 'react';
import { css, styled } from 'styled-components';
import { RuleSet } from 'styled-components/dist/types';
import { Color } from 'styles/theme';

// Type
export const Variant = ['primary', 'text'] as const;
export type Variant = (typeof Variant)[number];

export const Size = ['small', 'medium', 'large'] as const;
export type Size = (typeof Size)[number];

export const Align = ['left', 'center', 'right'] as const;
export type Align = (typeof Align)[number];

export type Props = {
  variant?: Variant;
  size?: Size;
  backgroundColor?: Color;
  fontColor?: Color;
  block?: boolean;
  align?: Align;
} & ComponentPropsWithRef<'button'>;

// Component
const Button = (
  {
    children,
    variant = 'primary',
    size = 'medium',
    block = false,
    align = 'center',
    ...rest
  }: Props,
  ref: ForwardedRef<HTMLButtonElement>,
) => {
  return (
    <StyledButton ref={ref} variant={variant} size={size} block={block} align={align} {...rest}>
      {children}
    </StyledButton>
  );
};

export default forwardRef(Button);

// Styled
const genVariantStyle = (variant: Required<Props>['variant']): RuleSet<object> => {
  const styles: Record<typeof variant, ReturnType<typeof genVariantStyle>> = {
    primary: css`
      color: ${({ theme }) => theme.color.gray10};
      outline: 1px solid ${({ theme }) => theme.color.gray1};

      &:hover {
        background-color: ${({ theme }) => theme.color.primary};
        color: ${({ theme }) => theme.color.gray2};
      }

      &:focus {
        box-shadow: 0 0 0 3px ${({ theme }) => theme.color.primary};
      }
    `,
    text: css`
      color: ${({ theme }) => theme.color.gray10};
      outline: 1px solid ${({ theme }) => theme.color.gray1};

      &:hover {
        background-color: ${({ theme }) => theme.color.gray4};
      }

      &:focus {
        box-shadow: 0 0 0 3px ${({ theme }) => theme.color.gray4};
      }
    `,
  };
  return styles[variant];
};

const genSizeStyle = (size: Required<Props>['size']): RuleSet<object> => {
  const styles: Record<typeof size, ReturnType<typeof genSizeStyle>> = {
    small: css`
      padding: 1rem 1.4rem;
      font-size: 1.4rem;
    `,
    medium: css`
      padding: 1.2rem 2rem;
      font-size: 1.6rem;
    `,
    large: css`
      padding: 1.4rem 2.4rem;
      font-size: 1.8rem;
    `,
  };
  return styles[size];
};

const StyledButton = styled.button<Props>`
  ${({ size = 'medium' }) => genSizeStyle(size)};
  ${({ variant = 'primary' }) => genVariantStyle(variant)};

  width: ${({ block }) => block && '100%'};
  border: none;
  border-radius: 4px;
  background-color: ${({ theme, disabled }) => (disabled ? theme.color.gray2 : theme.color.gray1)};
  text-align: ${({ align }) => align};
  transition: all 0.2s ease-in-out;
  cursor: pointer;
`;
