import { WritingIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import { useWritings } from './useWritings';
import DeleteButton from 'components/DeleteButton/DeleteButton';
import { useDeleteWritings } from './useDeleteWritings';
import { useGlobalStateValue } from '@yogjin/react-global-state-hook';
import { activeWritingInfoState } from 'globalState';

type Props = {
  categoryId: number;
  isOpen: boolean;
};

const WritingList = ({ categoryId, isOpen }: Props) => {
  const { goWritingPage } = usePageNavigate();
  const { writings } = useWritings(categoryId, isOpen);
  const activeWritingInfo = useGlobalStateValue(activeWritingInfoState);
  const writingId = activeWritingInfo?.id;
  const deleteWritings = useDeleteWritings();

  if (!writings || writings?.length === 0) return <S.NoWritingsText>빈 카테고리</S.NoWritingsText>;

  return (
    <ul>
      {writings.map((writing) => (
        <S.Item key={writing.id} $isClicked={writingId === writing.id}>
          <S.Button
            aria-label={`${writing.title}글 메인화면에 열기`}
            onClick={() => goWritingPage({ categoryId, writingId: writing.id })}
          >
            <S.IconWrapper>
              <WritingIcon width={14} height={14} />
            </S.IconWrapper>
            <S.Text>{writing.title}</S.Text>
          </S.Button>
          <S.DeleteButtonWrapper>
            <DeleteButton onClick={() => deleteWritings([writing.id])} />
          </S.DeleteButtonWrapper>
        </S.Item>
      ))}
    </ul>
  );
};

export default WritingList;

const S = {
  Item: styled.li<{ $isClicked: boolean }>`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 3.6rem;
    border-radius: 4px;
    background-color: ${({ theme, $isClicked }) => $isClicked && theme.color.gray4};

    &:hover {
      background-color: ${({ theme }) => theme.color.gray4};

      div {
        display: flex;
        flex-shrink: 0;
      }
    }
  `,

  Button: styled.button`
    display: flex;
    align-items: center;
    gap: 0.4rem;
    min-width: 0;
    height: 100%;
    padding: 0.4rem 0 0.4rem 3.2rem;
    border-radius: 4px;
  `,

  IconWrapper: styled.div`
    flex-shrink: 0;
  `,

  Text: styled.p`
    color: ${({ theme }) => theme.color.gray9};
    font-size: 1.4rem;
    font-weight: 400;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,

  NoWritingsText: styled.p`
    padding: 0.4rem 0 0.4rem 3.2rem;
    color: ${({ theme }) => theme.color.gray6};
    font-size: 1.4rem;
    font-weight: 500;
    cursor: default;
  `,

  DeleteButtonWrapper: styled.div`
    display: none;
    margin-right: 0.8rem;
  `,
};
