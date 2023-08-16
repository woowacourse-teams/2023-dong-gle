import Accordion from 'components/@common/Accordion/Accordion';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { styled } from 'styled-components';
import DeletedWritingList from './DeletedWritingList';

const TrashCan = () => {
  const { goTrashCanPage } = usePageNavigate();

  return (
    <Accordion>
      <Accordion.Item>
        <Accordion.Title>
          <S.Button aria-label='휴지통으로 이동하기' onClick={goTrashCanPage}>
            <S.Text>휴지통</S.Text>
          </S.Button>
        </Accordion.Title>
        <Accordion.Panel>
          <DeletedWritingList />
        </Accordion.Panel>
      </Accordion.Item>
    </Accordion>
  );
};

export default TrashCan;

const S = {
  Button: styled.button`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 3.6rem;
    border-radius: 8px;
    font-size: 1.4rem;

    &:hover {
      div {
        display: inline-flex;
        gap: 0.8rem;
      }
    }
  `,

  Text: styled.p`
    color: ${({ theme }) => theme.color.gray10};
    font-weight: 600;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  `,

  NoWritingsText: styled.p`
    padding: 0.8rem;
    color: ${({ theme }) => theme.color.gray6};
    font-size: 1.4rem;
    font-weight: 500;
    cursor: default;
  `,
};
