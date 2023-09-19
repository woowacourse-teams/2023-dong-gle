import styled, { css } from 'styled-components';
import { LeftArrowHeadIcon as ArrowIcon } from 'assets/icons';
import { Dispatch } from 'react';
import { Media } from '../Media/Media';

type Direction = 'left' | 'right';

type Props = {
  direction: Direction;
  medias: Media[];
  activeIndex: number;
  setActiveIndex: Dispatch<React.SetStateAction<number>>;
};

const PageNation = ({ direction, medias, activeIndex, setActiveIndex }: Props) => {
  const updateMediaIndex = () => {
    const indexUpdater = {
      left: activeIndex === 0 ? medias.length - 1 : activeIndex - 1,
      right: (activeIndex + 1) % medias.length,
    };

    setActiveIndex(indexUpdater[direction]);
  };

  return (
    <S.ActiveButton $direction={direction} onClick={updateMediaIndex}>
      <ArrowIcon width={12} height={36} />
    </S.ActiveButton>
  );
};

export default PageNation;

const S = {
  ActiveButton: styled.button<{ $direction: Direction }>`
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    bottom: 0;

    ${({ $direction }) =>
      $direction === 'left'
        ? css`
            left: 8px;
          `
        : css`
            right: 8px;
            svg {
              transform: rotate(180deg);
            }
          `}

    width: 2rem;
    height: 100%;
    z-index: 999;
  `,
};
