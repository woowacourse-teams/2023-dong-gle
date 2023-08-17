import Button from 'components/@common/Button/Button';
import Input from 'components/@common/Input/Input';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { DeletedWriting } from 'types/apis/trash';
import { useTrashCanTable } from './useTrashCanTable';

type Props = {
  writings: DeletedWriting[];
};

const TrashCanTable = ({ writings }: Props) => {
  const { goWritingPage } = usePageNavigate();
  const {
    rowRef,
    isAllCheckboxClicked,
    deletePermanentWritings,
    restoreDeletedWritings,
    toggleAllCheckbox,
    toggleCheckbox,
    getIsChecked,
  } = useTrashCanTable(writings);

  return (
    <S.Container>
      <S.ButtonContainer>
        <Button variant='secondary' size='small' onClick={deletePermanentWritings}>
          영구 삭제
        </Button>
        <Button variant='secondary' size='small' onClick={restoreDeletedWritings}>
          글 복구
        </Button>
      </S.ButtonContainer>
      <S.Table summary='휴지통 내부 글 목록을 나타낸다'>
        <colgroup>
          <col width='10%' />
          <col width='90%' />
        </colgroup>
        <thead>
          <tr ref={rowRef} tabIndex={0}>
            <th>
              <Input
                variant='unstyled'
                type='checkbox'
                checked={isAllCheckboxClicked}
                onClick={toggleAllCheckbox}
              />
            </th>
            <th>글 제목</th>
          </tr>
        </thead>
        <tbody>
          {writings.map(({ id, title, categoryId }) => (
            <tr key={id} tabIndex={0}>
              <td>
                <Input
                  variant='unstyled'
                  type='checkbox'
                  checked={getIsChecked(id)}
                  onChange={() => toggleCheckbox(id)}
                />
              </td>
              <td onClick={() => goWritingPage({ categoryId, writingId: id })}>{title}</td>
            </tr>
          ))}
        </tbody>
      </S.Table>
    </S.Container>
  );
};

export default TrashCanTable;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    align-items: end;
    gap: 2rem;
  `,

  ButtonContainer: styled.div`
    display: flex;
    gap: 0.8rem;
  `,

  Table: styled.table`
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
