import React, { useState, useLayoutEffect, CSSProperties } from 'react';
import PropTypes from 'prop-types';

const useDebouncedRippleCleanUp = (
  rippleCount: number,
  duration: number,
  cleanUpFunction: Function,
) => {
  useLayoutEffect(() => {
    let bounce: any = null;
    if (rippleCount > 0) {
      clearTimeout(bounce);

      bounce = setTimeout(() => {
        cleanUpFunction();
        clearTimeout(bounce);
      }, duration * 4);
    }

    return () => clearTimeout(bounce);
  }, [rippleCount, duration, cleanUpFunction]);
};

type Props = {
  duration?: number;
  color?: CSSProperties['color'];
};

type Ripple = {
  x: number;
  y: number;
  size: number;
};

/**
 *
 * @Ripple
 *
 * 물결이 퍼지는 효과를 추가하는 컴포넌트
 *
 * 사용방법: 효과를 적용하고 싶은 컴포넌트에 `<Ripple/>`을 children으로 선언한다.
 *
 *  `<Component>
 *    ...
 *    <Ripple/>
 *  </Component>`
 *
 * `Component` style에
 *  `{
 *    position: relative;
 *    overflow: hidden;
 *  }` 을 추가해야한다.
 */
const Ripple = ({ duration = 500, color = '#fff' }: Props) => {
  const [rippleArray, setRippleArray] = useState<Ripple[]>([]);

  useDebouncedRippleCleanUp(rippleArray.length, duration, () => {
    setRippleArray([]);
  });

  const addRipple = (event: any) => {
    const rippleContainer = event.currentTarget.getBoundingClientRect();
    const size =
      rippleContainer.width > rippleContainer.height
        ? rippleContainer.width
        : rippleContainer.height;
    const x = event.pageX - rippleContainer.x - size / 2;
    const y = event.pageY - rippleContainer.y - size / 2;
    const newRipple = {
      x,
      y,
      size,
    };

    setRippleArray([...rippleArray, newRipple]);
  };

  return (
    <S.RippleContainer duration={duration} color={color} onMouseDown={addRipple}>
      {rippleArray.length > 0 &&
        rippleArray.map((ripple, index) => {
          return (
            <span
              key={'span' + index}
              style={{
                top: ripple.y,
                left: ripple.x,
                width: ripple.size,
                height: ripple.size,
              }}
            />
          );
        })}
    </S.RippleContainer>
  );
};

export default Ripple;

import styled from 'styled-components';
import { Color } from 'styles/theme';

const S = {
  RippleContainer: styled.div<{ duration: number }>`
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;

    span {
      transform: scale(0);
      border-radius: 100%;
      position: absolute;
      opacity: 0.75;
      background-color: ${(props) => props.color};
      animation-name: ripple;
      animation-duration: ${(props) => props.duration}ms;
    }

    @keyframes ripple {
      to {
        opacity: 0;
        transform: scale(2);
      }
    }
  `,
};
