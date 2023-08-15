import Input from 'components/@common/Input/Input';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';
import { DeletedWriting } from 'types/apis/trash';

type Props = {
  writings: DeletedWriting[];
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
        <col width='90%' />
      </colgroup>
      <thead>
        <tr ref={rowRef} tabIndex={0}>
          <th>
            <Input variant='unstyled' type='checkbox' onClick={toggleAllCheckbox} />
          </th>
          <th>글 제목</th>
        </tr>
      </thead>
      <tbody>
        {writings.map(({ id, title }) => (
          <tr key={id} tabIndex={0}>
            <td>
              <Input
                variant='unstyled'
                type='checkbox'
                checked={writingIds.includes(id)}
                onChange={() => toggleCheckbox(id)}
              />
            </td>
            <td onClick={() => goWritingPage({ categoryId, writingId: id })}>{title}</td>
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
};
