import { useState } from 'react';
import styled from 'styled-components';
import Media, { Media as MediaType } from './Media/Media';
import PageNation from './PageNation/PageNation';

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
      <Media media={medias[activeIndex]} />
      <PageNation
        direction='right'
        medias={medias}
        activeIndex={activeIndex}
        setActiveIndex={setActiveIndex}
      />
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
};

export default Carousel;
