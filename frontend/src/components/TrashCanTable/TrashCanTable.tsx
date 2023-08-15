import Input from 'components/@common/Input/Input';
import { BLOG_ICON } from 'constants/blog';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { Fragment, MouseEvent, useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';
import { Writing } from 'types/apis/writings';
import { dateFormatter } from 'utils/date';

type Props = {
  writings: Writing[];
  categoryId: number;
};

const TrashCanTable = ({ writings, categoryId }: Props) => {
  const [writingIds, setWritingIds] = useState<number[]>([]);
  const { goWritingPage } = usePageNavigate();
  const rowRef = useRef<HTMLTableRowElement>(null);

  useEffect(() => {
    rowRef.current?.focus();
  }, [writings]);

  const toggleAllCheckbox = () => {
    if (writingIds.length === writings.length) {
      setWritingIds([]); // 모두 체크해제
    } else {
      const allWritingIds = writings.map((writing) => writing.id);
      setWritingIds(allWritingIds); // 모두 체크
    }
  };

  const toggleCheckbox = (id: number) => {
    if (writingIds.includes(id)) {
      setWritingIds(writingIds.filter((writingId) => writingId !== id));
    } else {
      setWritingIds([...writingIds, id]);
    }
  };

  return (
    <S.TrashCanTableContainer summary='카테고리 내부 글 목록을 나타낸'>
      <colgroup>
        <col width='10%' />
        <col width='60%' />
        <col width='15%' />
        <col width='15%' />
      </colgroup>
      <thead>
        <tr ref={rowRef} tabIndex={0}>
          <th>
            <Input variant='unstyled' type='checkbox' onClick={toggleAllCheckbox} />
          </th>
          <th>글 제목</th>
          <th>발행한 블로그 플랫폼</th>
          <th>발행 시간</th>
        </tr>
      </thead>
      <tbody>
        {writings.map(({ id, title, publishedDetails, createdAt }) => (
          <tr key={id} tabIndex={0}>
            <td>
              <Input
                variant='unstyled'
                type='checkbox'
                checked={writingIds.includes(id)} // 여기서 체크 상태를 제어합니다.
                onChange={() => toggleCheckbox(id)}
              />
            </td>
            <td onClick={() => goWritingPage({ categoryId, writingId: id })}>{title}</td>
            <td onClick={() => goWritingPage({ categoryId, writingId: id })}>
              <S.PublishedToIconContainer>
                {publishedDetails.map(({ blogName }, index) => (
                  <Fragment key={index}>{BLOG_ICON[blogName]}</Fragment>
                ))}
              </S.PublishedToIconContainer>
            </td>
            <td onClick={() => goWritingPage({ categoryId, writingId: id })}>
              {dateFormatter(createdAt, 'YYYY.MM.DD.')}
            </td>
          </tr>
        ))}
      </tbody>
    </S.TrashCanTableContainer>
  );
};

export default TrashCanTable;

const S = {
  TrashCanTableContainer: styled.table`
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

    tbody tr:hover td:not(:first-child) {
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
