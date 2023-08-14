import { useState, useLayoutEffect, HTMLAttributes, AnimationEventHandler } from 'react';
import { css, styled } from 'styled-components';
import * as animation from 'styles/animation';

type DisplayState = 'hidden' | 'appear' | 'disappear' | 'show';
type Animation = 'slide';
type DirectionTo = 'up' | 'down' | 'left' | 'right';

type Props = {
  appearAnimation?: Animation;
  disappearAnimation?: Animation;
  animationDuration?: number;
  directionTo?: DirectionTo;
  isVisible: boolean;
  children: React.ReactNode;
  onAppear?: AnimationEventHandler<HTMLDivElement>;
  onDisappear?: AnimationEventHandler<HTMLDivElement>;
} & Omit<HTMLAttributes<HTMLDivElement>, 'onAnimationEnd'>;

// hidden -> appear(애니메이션) -> show -> disappear(애니메이션) -> hidden 반복.
const AnimationDiv = ({
  appearAnimation = 'slide',
  disappearAnimation = 'slide',
  animationDuration = 0.5,
  directionTo = 'up',
  isVisible,
  children,
  onAppear,
  onDisappear,
  ...rest
}: Props) => {
  const [displayState, setDisplayState] = useState<DisplayState>('hidden');

  // 바깥에서 받아온 isVisible 상태로 appear할 건지 disappear 할 건지 결정.
  // isVisible을 true로 받으면 appear, display가 show인 상태에서 isVisible을 false로 받으면 disappear.
  useLayoutEffect(
    function checkDisplayState() {
      const isConditionAppear = isVisible;
      const isConditionDisappear = displayState === 'show' && !isVisible;

      if (!isConditionAppear && !isConditionDisappear) return;

      setDisplayState(isVisible ? 'appear' : 'disappear');
    },
    [isVisible],
  );

  // hidden 상태인데 isVisible도 false라면 아무것도 렌더링 되지 않아야함.
  const isUnmounted = isVisible === false && displayState === 'hidden';

  if (isUnmounted) return <></>;

  // appear와 disappear 애니메이션이 끝나고(onAnimationEnd) displayState를 업데이트.
  const updateDisplayState: AnimationEventHandler<HTMLDivElement> = (e) => {
    if (displayState === 'appear') {
      setDisplayState('show');
      onAppear?.(e);
    }

    if (displayState === 'disappear') {
      setDisplayState('hidden');
      onDisappear?.(e);
    }
  };

  // 현재 실행할 애니메이션 결정.
  const currentAnimation = isVisible ? appearAnimation : disappearAnimation;

  return (
    <S.Wrapper
      $displayState={displayState}
      $directionTo={directionTo}
      $animation={currentAnimation}
      $duration={animationDuration}
      onAnimationEnd={updateDisplayState}
      {...rest}
    >
      {children}
    </S.Wrapper>
  );
};

export default AnimationDiv;

const genAnimationStyle = (
  displayState: DisplayState,
  animationType: Animation,
  direction: DirectionTo,
) => {
  if (displayState === 'show' || displayState === 'hidden') return 'none';
  if (animationType === 'slide') {
    if (direction === 'down') return animation.slideToDown;
    if (direction === 'up') return animation.slideToUp;
    if (direction === 'right') return animation.slideToRight;
    if (direction === 'left') return animation.slideToLeft;
  }
};

const S = {
  Wrapper: styled.div<{
    $displayState: DisplayState;
    $directionTo: DirectionTo;
    $animation: Animation;
    $duration: number;
  }>`
    animation: ${({ $displayState, $animation, $directionTo, $duration }) => css`
      ${genAnimationStyle($displayState, $animation, $directionTo)} ${$duration}s
    `};
    ${({ $displayState }) =>
      $displayState === 'disappear'
        ? css`
            animation-direction: reverse;
            animation-fill-mode: forwards;
          `
        : css`
            animation-direction: normal;
            animation-fill-mode: normal;
          `}
  `,
};
