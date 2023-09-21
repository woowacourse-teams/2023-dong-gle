import { Fragment } from 'react';
import styled from 'styled-components';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { dateFormatter } from 'utils/date';
import { blogIcon } from 'components/WritingTable/WritingTable';
import Pagination from 'components/@common/Pagination/Pagination';
import { useHomeTable } from './useHomeTable';

type Props = {
  initialPageIndex?: number;
};

const HomeTable = ({ initialPageIndex = 0 }: Props) => {
  const { content, totalPages, rowRef, activePage, changeActivePage } =
    useHomeTable(initialPageIndex);
  const { goWritingPage } = usePageNavigate();

  if (!content || !totalPages) return null;

  return (
    <S.Container>
      <S.HomeTable summary='카테고리 내부 글 목록을 나타낸다'>
        <colgroup>
          <col width='20%' />
          <col width='40%' />
          <col width='20%' />
          <col width='20%' />
        </colgroup>
        <thead>
          <tr ref={rowRef} tabIndex={0}>
            <th>카테고리</th>
            <th>글 제목</th>
            <th>생성 날짜</th>
            <th>발행한 블로그 플랫폼</th>
          </tr>
        </thead>
        <tbody>
          {content.map(
            ({
              id,
              title,
              publishedDetails,
              createdAt,
              category: { id: categoryId, categoryName },
            }) => (
              <tr
                key={id}
                onClick={() =>
                  goWritingPage({ categoryId, writingId: id, isDeletedWriting: false })
                }
                role='button'
                tabIndex={0}
              >
                <td>{categoryName}</td>
                <td>{title}</td>
                <td>{dateFormatter(createdAt, 'YYYY.MM.DD.')}</td>
                <td>
                  <S.PublishedToIconContainer>
                    {publishedDetails.map(({ blogName }, index) => (
                      <Fragment key={index}>{blogIcon[blogName]}</Fragment>
                    ))}
                  </S.PublishedToIconContainer>
                </td>
              </tr>
            ),
          )}
        </tbody>
      </S.HomeTable>
      <Pagination
        pageLength={totalPages}
        activePage={activePage}
        changeActivePage={changeActivePage}
      />
    </S.Container>
  );
};

export default HomeTable;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 50px;
  `,

  HomeTable: styled.table`
    width: 100%;
    text-align: left;
    font-size: 1.4rem;

    th {
      color: ${({ theme }) => theme.color.gray8};
    }

    td {
      .publishedTo {
        display: flex;
        gap: 0.8rem;
      }
    }

    th,
    td {
      padding: 1.1rem;
      border: 1px solid ${({ theme }) => theme.color.gray5};
    }

    th:first-child,
    td:first-child {
      border-left: none;
    }

    th:last-child,
    td:last-child {
      border-right: none;
    }

    tbody tr:hover {
      cursor: pointer;
      transform: scale(1.01);
      transition: all 300ms;
    }
  `,

  PublishedToIconContainer: styled.div`
    display: flex;
    gap: 0.8rem;
  `,
};
