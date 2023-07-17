import { css, styled } from 'styled-components';
import { rotation } from 'styles/animation';
import { theme } from 'styles/theme';

type Props = {
  size: number;
  thickness: number;
  duration: number;
  backgroundColor: string;
  barColor: string;
};

const Spinner = ({
  size = 30,
  thickness = 4,
  duration = 1,
  backgroundColor = theme.color.gray4,
  barColor = theme.color.primary,
}: Partial<Props>) => {
  return (
    <S.Spinner
      size={size}
      thickness={thickness}
      duration={duration}
      backgroundColor={backgroundColor}
      barColor={barColor}
    />
  );
};

export default Spinner;

const S = {
  Spinner: styled.div<Props>`
    ${({ size, thickness, backgroundColor, barColor, duration }) => {
      return css`
        display: inline-block;

        width: ${size}px;
        height: ${size}px;

        border: ${thickness}px solid ${backgroundColor};
        border-bottom-color: ${barColor};
        border-radius: 50%;

        animation: ${rotation} ${duration}s linear infinite;
      `;
    }}
  `,
};
