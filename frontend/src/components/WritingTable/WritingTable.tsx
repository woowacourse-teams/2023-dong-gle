import { MediumLogoIcon, TistoryLogoIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { Fragment, ReactElement, useEffect, useRef } from 'react';
import { styled } from 'styled-components';
import { Writing } from 'types/apis/writings';
import { Blog } from 'types/domain';
import { dateFormatter } from 'utils/date';

type Props = {
  writings: Writing[];
  categoryId: number;
  isMobile?: boolean;
};

export const blogIcon: Record<Blog, ReactElement> = {
  MEDIUM: <MediumLogoIcon width='2.4rem' height='2.4rem' />,
  TISTORY: <TistoryLogoIcon width='2.4rem' height='2.4rem' />,
};

const WritingTable = ({ writings, categoryId, isMobile = false }: Props) => {
  const { goWritingPage } = usePageNavigate();
  const rowRef = useRef<HTMLTableRowElement>(null);

  useEffect(() => {
    rowRef.current?.focus();
  }, [writings]);

  return (
    <S.WritingTableContainer summary='카테고리 내부 글 목록을 나타낸'>
      <colgroup>
        <col width='60%' />
        <col width='20%' />
        {isMobile ? null : <col width='20%' />}
      </colgroup>
      <thead>
        <tr ref={rowRef} tabIndex={0}>
          <th>글 제목</th>
          {isMobile ? null : <th>생성 날짜</th>}
          {isMobile ? <th>블로그</th> : <th>발행한 블로그 플랫폼</th>}
        </tr>
      </thead>
      <tbody>
        {writings.map(({ id, title, publishedDetails, createdAt }) => (
          <tr
            key={id}
            onClick={() => goWritingPage({ categoryId, writingId: id, isDeletedWriting: false })}
            role='button'
            tabIndex={0}
          >
            <td>{title}</td>
            {isMobile ? null : <td>{dateFormatter(createdAt, 'YYYY.MM.DD.')}</td>}
            <td>
              <S.PublishedToIconContainer>
                {publishedDetails.map(({ blogName }, index) => (
                  <Fragment key={index}>{blogIcon[blogName]}</Fragment>
                ))}
              </S.PublishedToIconContainer>
            </td>
          </tr>
        ))}
      </tbody>
    </S.WritingTableContainer>
  );
};

export default WritingTable;

const S = {
  WritingTableContainer: styled.table`
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
