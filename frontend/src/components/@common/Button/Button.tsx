// import { HomeIcon } from 'assets/icons';
import {
  CSSProperties,
  ComponentPropsWithRef,
  ForwardedRef,
  ReactElement,
  forwardRef,
} from 'react';

import { css, styled } from 'styled-components';
import { RuleSet } from 'styled-components/dist/types';

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
  block?: boolean;
  align?: Align;
  icon?: ReactElement;
} & ComponentPropsWithRef<'button'>;

// Component
const Button = (
  {
    children,
    variant = 'primary',
    size = 'medium',
    block = false,
    align = 'center',
    icon,
    ...rest
  }: Props,
  ref: ForwardedRef<HTMLButtonElement>,
) => {
  return (
    <S.Button ref={ref} variant={variant} size={size} block={block} align={align} {...rest}>
      <S.IconTextContainer>
        {Boolean(icon) && <S.IconWrapper size={size}>{icon}</S.IconWrapper>}
        <p>{children}</p>
      </S.IconTextContainer>
    </S.Button>
  );
};

export default forwardRef(Button);

// Styled
const genVariantStyle = (variant: Required<Props>['variant']): RuleSet<object> => {
  const styles: Record<typeof variant, ReturnType<typeof genVariantStyle>> = {
    primary: css`
      color: ${({ theme }) => theme.color.gray10};
      background-color: ${({ theme }) => theme.color.gray1};
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
      background-color: ${({ theme }) => theme.color.gray1};
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

const genIconStyle = (size: Required<Props>['size']): RuleSet<object> => {
  const styles: Record<typeof size, ReturnType<typeof genIconStyle>> = {
    small: css`
      width: 2rem;
      height: 2rem;
    `,
    medium: css`
      width: 2.2rem;
      height: 2.2rem;
    `,
    large: css`
      width: 2.4rem;
      height: 2.4rem;
    `,
  };
  return styles[size];
};

const genAlignStyle = (align: Required<Props>['align']): RuleSet<object> => {
  const genAlign: Record<typeof align, CSSProperties['justifyContent']> = {
    left: 'flex-start',
    center: 'space-evenly',
    right: 'flex-end',
  };

  return css`
    justify-content: ${genAlign[align]};
    align-items: center;
  `;
};

const S = {
  Button: styled.button<Props>`
    ${({ size = 'medium' }) => genSizeStyle(size)};
    ${({ variant = 'primary' }) => genVariantStyle(variant)};
    ${({ align = 'center' }) => genAlignStyle(align)};

    display: flex;
    width: ${({ block }) => block && '100%'};
    border: none;
    border-radius: 4px;

    transition: all 0.2s ease-in-out;
    cursor: pointer;
  `,

  IconTextContainer: styled.div<Props>`
    display: flex;
    align-items: center;
    gap: 1rem;
  `,

  IconWrapper: styled.div<Props>`
    ${({ size = 'medium' }) => genIconStyle(size)};
  `,
};
