import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { Writing } from 'types/apis/writings';
import { dateFormatter } from 'utils/date';

type Props = { writings: Writing[] };

const WritingTable = ({ writings }: Props) => {
  const navigate = useNavigate();

  const goWritingPage = (writingId: number) => navigate(`/writing/${writingId}`);

  return (
    <>
      <S.WritingTableContainer>
        <tr>
          <th>Title</th>
          <th>Published To</th>
          <th>Published Time</th>
        </tr>
        {writings.map((writing) => (
          <tr key={writing.id} onClick={() => goWritingPage(writing.id)}>
            <td>{writing.title}</td>
            <td>{writing.publishedDetails[0].blogName}</td>
            <td>{dateFormatter(writing.createdAt, 'YYYY.MM.DD.')}</td>
          </tr>
        ))}
      </S.WritingTableContainer>
    </>
  );
};

export default WritingTable;

const S = {
  WritingTableContainer: styled.table`
    width: 100%;
    border-left: none;
    text-align: left;
    font-size: 1.4rem;

    th {
      color: ${({ theme }) => theme.color.gray7};
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

    tr:not(:first-child):hover {
      cursor: pointer;
      transform: scale(1.01);
      transition: all 300ms;
    }
  `,
};
