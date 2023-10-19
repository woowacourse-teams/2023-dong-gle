import Button from 'components/@common/Button/Button';
import Input from 'components/@common/Input/Input';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { css, styled } from 'styled-components';
import { DeletedWriting } from 'types/apis/trash';
import { useTrashCanTable } from './useTrashCanTable';
import { MAX_WIDTH } from 'constants/style';

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
          복구
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
                onChange={toggleAllCheckbox}
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
                  aria-label={`휴지통 글 ${title} 선택`}
                />
              </td>
              <td
                onClick={() => goWritingPage({ categoryId, writingId: id, isDeletedWriting: true })}
              >
                {title}
              </td>
            </tr>
          ))}
        </tbody>
      </S.Table>
    </S.Container>
  );
};

export default TrashCanTable;

const generateResponsiveStyle = {
  buttonContainer: css`
    @media (max-width: ${MAX_WIDTH.mobileSmall}) {
      button {
        padding: 0.6rem 1rem;
        font-size: 1.2rem;
      }
    }
  `,
};

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

    ${() => generateResponsiveStyle.buttonContainer}
  `,

  Table: styled.table`
    width: 100%;
    text-align: left;
    font-size: 1.4rem;
    table-layout: fixed;

    th {
      color: ${({ theme }) => theme.color.gray8};
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
      padding: 1.1rem;
      border: 1px solid ${({ theme }) => theme.color.gray5};
    }

    th:first-child,
    td:first-child {
      padding: 0 0.1rem;
      border-left: none;

      input {
        cursor: pointer;
      }
    }

    th:last-child,
    td:last-child {
      border-right: none;
    }

    tbody tr:hover td:not(:first-child) {
      cursor: pointer;
      background-color: ${({ theme }) => theme.color.gray3};
    }
  `,
};
