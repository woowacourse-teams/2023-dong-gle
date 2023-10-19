import { useGlobalStateValue } from '@yogjin/react-global-state';
import { mediaQueryMobileState } from 'globalState';
import styled from 'styled-components';

type Props = {
  pageLength: number;
  activePage: number;
  changeActivePage: (pageIndex: number) => void;
};

const Pagination = ({ pageLength, activePage, changeActivePage }: Props) => {
  const isMobile = useGlobalStateValue(mediaQueryMobileState);
  const isShowPrevious = activePage > 0;
  const isShowNext = activePage < pageLength - 1;

  // 시작 페이지와 끝 페이지의 범위를 설정해주는 함수
  const setPageRange = () => {
    const isBetween1and4 = activePage < 4;
    const isNearLast4Pages = activePage >= pageLength - 5;

    if (isBetween1and4) return [0, Math.min(8, pageLength - 1)];

    if (isNearLast4Pages) return [Math.max(pageLength - 9, 0), pageLength - 1];

    return [activePage - 4, activePage + 4];
  };

  const [startPage, endPage] = setPageRange();

  return (
    <S.Container>
      <S.PaginationButton
        disabled={!isShowPrevious}
        onClick={() => changeActivePage(activePage - 1)}
      >
        {'<'}
      </S.PaginationButton>
      {isMobile ? (
        <S.PageButton $isActive={true} onClick={() => {}}>
          {activePage + 1}
        </S.PageButton>
      ) : (
        Array.from({ length: endPage - startPage + 1 }, (_, index) => {
          const pageIndex = startPage + index;

          return (
            <li key={pageIndex}>
              <S.PageButton
                $isActive={pageIndex === activePage}
                onClick={() => changeActivePage(pageIndex)}
              >
                {pageIndex + 1}
              </S.PageButton>
            </li>
          );
        })
      )}
      <S.PaginationButton disabled={!isShowNext} onClick={() => changeActivePage(activePage + 1)}>
        {'>'}
      </S.PaginationButton>
    </S.Container>
  );
};

export default Pagination;

const S = {
  Container: styled.div`
    display: flex;
    gap: 16px;
  `,

  PaginationButton: styled.button`
    width: 2.8rem;
    height: 2.8rem;
    border-radius: 4px;

    font-size: 2rem;

    &:disabled {
      &:hover {
        background-color: transparent;
      }
      cursor: default;
    }

    &:hover {
      background-color: ${({ theme }) => theme.color.gray3};
    }
  `,

  PageButton: styled.button<{ $isActive: boolean }>`
    width: 2.8rem;
    height: 2.8rem;
    background-color: ${({ theme, $isActive }) => ($isActive ? theme.color.gray3 : 'inherit')};
    border-radius: 4px;

    font-size: 1.6rem;
    font-weight: ${({ $isActive }) => ($isActive ? 600 : 400)};

    &:hover {
      background-color: ${({ theme }) => theme.color.gray3};
    }
  `,
};
