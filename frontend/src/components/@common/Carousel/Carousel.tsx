import { useState } from 'react';
import styled from 'styled-components';
import Media, { Media as MediaType } from './Media/Media';
import PageNation from './PageNation/PageNation';
import Indicator from './Indicator/Indicator';

type Props = {
  medias: MediaType[];
  width?: string;
  height?: string;
};

const Carousel = ({ medias, width = '640px', height = '360px' }: Props) => {
  const [activeIndex, setActiveIndex] = useState(0);

  return (
    <S.Carousel $width={width} $height={height}>
      <PageNation
        direction='left'
        medias={medias}
        activeIndex={activeIndex}
        setActiveIndex={setActiveIndex}
      />
      <S.MediaAnimationContainer $translateX={`-${activeIndex * 100}%`}>
        {medias.map((media, index) => (
          <Media key={index} media={media} />
        ))}
      </S.MediaAnimationContainer>
      <PageNation
        direction='right'
        medias={medias}
        activeIndex={activeIndex}
        setActiveIndex={setActiveIndex}
      />
      <Indicator length={medias.length} activeIndex={activeIndex} setActiveIndex={setActiveIndex} />
    </S.Carousel>
  );
};

const S = {
  Carousel: styled.div<{ $width: string; $height: string }>`
    display: flex;
    position: relative;
    width: ${({ $width }) => $width};
    height: ${({ $height }) => $height};
    overflow: hidden;
  `,

  MediaAnimationContainer: styled.ul<{ $translateX: string }>`
    display: flex;
    width: 100%;
    height: 100%;

    transition: all 0.5s ease-in-out;
    transform: translateX(${({ $translateX }) => $translateX});
  `,
};

export default Carousel;
