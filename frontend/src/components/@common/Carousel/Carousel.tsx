import { LeftArrowHeadIcon as ArrowIcon } from 'assets/icons';
import { useState } from 'react';
import styled, { css } from 'styled-components';
import Media, { Media as MediaType } from './Media/Media';

type Props = {
  medias: MediaType[];
  width?: string;
  height?: string;
};

const Carousel = ({ medias, width = '640px', height = '360px' }: Props) => {
  const [activeIndex, setActiveIndex] = useState(0);

  const activePreviousMedia = () => {
    const newIndex = activeIndex === 0 ? medias.length - 1 : activeIndex - 1;
    setActiveIndex(newIndex);
  };

  const activeNextMedia = () => {
    const newIndex = (activeIndex + 1) % medias.length;
    setActiveIndex(newIndex);
  };

  return (
    <S.Carousel $width={width} $height={height}>
      <S.ActiveButton $left onClick={activePreviousMedia}>
        <ArrowIcon width={12} height={36} />
      </S.ActiveButton>
      <Media media={medias[activeIndex]} />
      <S.ActiveButton $right onClick={activeNextMedia}>
        <ArrowIcon width={12} height={40} />
      </S.ActiveButton>
    </S.Carousel>
  );
};

const S = {
  Carousel: styled.div<{ $width: string; $height: string }>`
    display: flex;
    position: relative;
    width: ${({ $width }) => $width};
    height: ${({ $height }) => $height};
  `,

  ActiveButton: styled.button<{ $left?: boolean; $right?: boolean }>`
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    bottom: 0;

    ${({ $left }) =>
      $left &&
      css`
        left: 8px;
      `}
    ${({ $right }) =>
      $right &&
      css`
        right: 8px;

        svg {
          transform: rotate(180deg);
        }
      `}

    width: 2rem;
    height: 100%;
  `,
};

export default Carousel;
