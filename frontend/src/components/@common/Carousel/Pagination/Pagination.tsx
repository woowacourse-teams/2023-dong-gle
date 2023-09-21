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

const Pagination = ({ direction, medias, activeIndex, setActiveIndex }: Props) => {
  const updateMediaIndex = () => {
    const indexUpdater = {
      left: activeIndex === 0 ? medias.length - 1 : activeIndex - 1,
      right: (activeIndex + 1) % medias.length,
    };

    setActiveIndex(indexUpdater[direction]);
  };

  return (
    <S.ActiveButton $direction={direction} onClick={updateMediaIndex}>
      <ArrowIcon width={20} height={36} />
    </S.ActiveButton>
  );
};

export default Pagination;

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
            left: 0rem;
          `
        : css`
            right: 0rem;
            svg {
              transform: rotate(180deg);
            }
          `}

    width: 4rem;
    height: 100%;
    z-index: 999;
  `,
};
