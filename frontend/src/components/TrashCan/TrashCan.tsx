import Accordion from 'components/@common/Accordion/Accordion';
import { styled } from 'styled-components';

const TrashCan = () => {
  return (
    <Accordion>
      <Accordion.Item>
        <Accordion.Title>
          <S.Button
            // onClick={() => goWritingTablePage(categoryId)}
            aria-label='휴지통으로 이동하기'
          >
            <S.Text>휴지통</S.Text>
          </S.Button>
        </Accordion.Title>
        <Accordion.Panel></Accordion.Panel>
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
    height: 3.2rem;
    padding: 0.8rem;
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
};
