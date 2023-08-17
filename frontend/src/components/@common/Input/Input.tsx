import type { ComponentPropsWithRef, ForwardedRef, ReactElement } from 'react';
import { forwardRef, useId } from 'react';
import { RuleSet, css, styled } from 'styled-components';
import { Size } from 'types/components/common';

export const InputVariant = ['outline', 'filled', 'unstyled', 'underlined'] as const;
export type InputVariant = (typeof InputVariant)[number];

type Props = {
  size: Size;
  labelText: string;
  supportingText: string;
  variant: InputVariant;
  isError: boolean;
} & Omit<ComponentPropsWithRef<'input'>, 'size'>;

const Input = (
  {
    size = 'medium',
    labelText,
    supportingText,
    variant = 'outline',
    isError = false,
    ...rest
  }: Partial<Props>,
  ref: ForwardedRef<HTMLInputElement>,
) => {
  const inputId = useId();
  return (
    <S.InputContainer>
      {labelText && (
        <S.Label htmlFor={inputId} $required={rest.required} $variant={variant}>
          {labelText}
        </S.Label>
      )}
      <S.Input
        id={inputId}
        ref={ref}
        $size={size}
        $variant={variant}
        $isError={isError}
        {...rest}
      />
      {supportingText && <S.SupportingText $isError={isError}>{supportingText}</S.SupportingText>}
    </S.InputContainer>
  );
};

export default forwardRef(Input);

const genVariantStyle = (
  variant: Required<Props>['variant'],
  isError: Required<Props>['isError'],
): RuleSet<object> => {
  const styles: Record<typeof variant, ReturnType<typeof genVariantStyle>> = {
    outline: css`
      ${({ theme }) => css`
        border: 1px solid ${isError ? theme.color.red6 : theme.color.gray6};
        outline: 1px solid transparent;

        &:focus {
          border: 1px solid ${isError ? theme.color.red6 : theme.color.gray6};
          outline: 1px solid ${isError ? theme.color.red6 : theme.color.gray8};
        }
      `}
    `,
    filled: css`
      ${({ theme }) => css`
        background-color: ${isError ? theme.color.red1 : theme.color.gray4};
        border: 1px solid transparent;
        outline: 1px solid transparent;

        &:focus {
          background-color: transparent;
          outline: 1px solid ${isError ? theme.color.red6 : theme.color.gray8};
        }
      `}
    `,
    unstyled: css`
      ${({ theme }) => css`
        border: 1px solid transparent;
        outline: 1px solid ${isError ? theme.color.red6 : theme.color.gray1};
      `}
    `,
    underlined: css`
      ${({ theme }) => css`
        border: 1px solid transparent;
        border-bottom: 1px solid ${isError ? theme.color.red6 : theme.color.gray6};
        border-radius: 0;
        outline: 1px solid transparent;
      `}
    `,
  };
  return styles[variant];
};

const genSizeStyle = (size: Required<Props>['size']): RuleSet<object> => {
  const styles: Record<typeof size, ReturnType<typeof genSizeStyle>> = {
    small: css`
      padding: 0.6rem 0.6rem;
      font-size: 1.3rem;
    `,
    medium: css`
      padding: 0.8rem 1rem;
      font-size: 1.4rem;
    `,
    large: css`
      padding: 1rem 1.2rem;
      font-size: 1.5rem;
    `,
  };
  return styles[size];
};

const S = {
  InputContainer: styled.div`
    display: flex;
    flex-direction: column;
    gap: 0.6rem;
    font-size: 1.3rem;
  `,

  Label: styled.label<{ $required: boolean | undefined; $variant: InputVariant }>`
    font-weight: 500;
    ${({ $required, theme }) =>
      $required &&
      css`
        &::after {
          content: '*';
          margin-left: 0.2rem;
          color: ${theme.color.red6};
        }
      `};
  `,
  Input: styled.input<{
    $size: Size;
    $variant: InputVariant;
    $isError: boolean;
  }>`
    border: none;
    border-radius: 4px;
    background-color: transparent;

    ${({ $size }) => genSizeStyle($size)};
    ${({ $variant, $isError }) => genVariantStyle($variant, $isError)};

    &::placeholder {
      color: ${({ theme }) => theme.color.gray6};
    }
  `,
  SupportingText: styled.p<{ $isError: boolean | undefined }>`
    color: ${({ $isError, theme }) => ($isError ? theme.color.red6 : theme.color.gray7)};
  `,
  Underline: styled.div`
    position: absolute;
    bottom: 0;
    left: 0;
    height: 2px;
    width: 100%;
    background-color: ${({ theme }) => theme.color.primary};
    transform: scaleX(0);
    transition: all 0.3s ease;
  `,
};
