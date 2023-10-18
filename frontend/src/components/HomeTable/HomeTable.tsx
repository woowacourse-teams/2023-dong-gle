import { Fragment } from 'react';
import styled from 'styled-components';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { dateFormatter } from 'utils/date';
import { blogIcon } from 'components/WritingTable/WritingTable';
import Pagination from 'components/@common/Pagination/Pagination';
import { useHomeTable } from './useHomeTable';
import { EmptyWritingTableIcon } from 'assets/icons';

type Props = {
  initialPageIndex?: number;
  isMobile?: boolean;
};

const HomeTable = ({ initialPageIndex = 0, isMobile = false }: Props) => {
  const { content, totalPages, rowRef, activePage, changeActivePage } =
    useHomeTable(initialPageIndex);
  const { goWritingPage } = usePageNavigate();

  if (!content || !totalPages)
    return (
      <S.EmptyMessage>
        <EmptyWritingTableIcon width={80} height={80} />
        <S.AddWritingTextContainer>
          <S.AddWritingText>글이 없습니다.</S.AddWritingText>
          <S.AddWritingText>글 가져오기를 통해 글을 추가해 보세요!</S.AddWritingText>
        </S.AddWritingTextContainer>
      </S.EmptyMessage>
    );

  return (
    <S.Container>
      <S.HomeTable summary='카테고리 내부 글 목록을 나타낸다'>
        <colgroup>
          <col width='30%' />
          <col width='70%' />
          {isMobile ? null : <col width='20%' />}
          {isMobile ? null : <col width='20%' />}
        </colgroup>
        <thead>
          <tr ref={rowRef} tabIndex={0}>
            <th>카테고리</th>
            <th>글 제목</th>
            {isMobile ? null : <th>생성 날짜</th>}
            {isMobile ? null : <th>발행한 블로그 플랫폼</th>}
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
                {isMobile ? null : <td>{dateFormatter(createdAt, 'YYYY.MM.DD.')}</td>}
                {isMobile ? null : (
                  <td>
                    <S.PublishedToIconContainer>
                      {publishedDetails.map(({ blogName }, index) => (
                        <Fragment key={index}>{blogIcon[blogName]}</Fragment>
                      ))}
                    </S.PublishedToIconContainer>
                  </td>
                )}
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
    position: relative;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 50px;
  `,

  EmptyMessage: styled.p`
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 1.6rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
  `,

  AddWritingTextContainer: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 5px;
  `,

  AddWritingText: styled.p``,

  HomeTable: styled.table`
    width: 100%;
    text-align: left;
    font-size: 1.4rem;
    table-layout: fixed;

    th {
      color: ${({ theme }) => theme.color.gray8};
    }

    tr {
      height: 4.2rem;
    }

    td {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;

      .publishedTo {
        display: flex;
        gap: 0.8rem;
      }
    }

    th,
    td {
      padding: 0 1rem;
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
      background-color: ${({ theme }) => theme.color.gray3};
    }
  `,

  PublishedToIconContainer: styled.div`
    display: flex;
    gap: 0.8rem;
  `,
};
