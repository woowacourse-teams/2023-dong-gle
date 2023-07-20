import { css, styled } from 'styled-components';
import { theme } from 'styles/theme';

type Props = {
  length?: string;
  direction?: 'horizontal' | 'vertical';
};

const Divider = ({ length = '100%', direction = 'horizontal' }: Props) => {
  return <S.Divider $length={length} $direction={direction} />;
};

export default Divider;

export const genDirectionStyling = (
  direction: Required<Props>['direction'],
  length: Required<Props>['length'],
) => {
  const style = {
    horizontal: css`
      border-bottom: 1px solid ${theme.color.gray5};
      width: ${length};
    `,
    vertical: css`
      border-left: 1px solid ${theme.color.gray5};
      height: ${length};
    `,
  };
  return style[direction];
};

const S = {
  Divider: styled.div<{ $length: string; $direction: 'horizontal' | 'vertical' }>`
    ${({ $direction, $length }) => genDirectionStyling($direction, $length)};
  `,
};
