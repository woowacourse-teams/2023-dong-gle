import { MediumLogoIcon, TistoryLogoIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { Fragment } from 'react';
import { styled } from 'styled-components';
import { Writing } from 'types/apis/writings';
import { Blog } from 'types/domain';
import { dateFormatter } from 'utils/date';

type Props = { writings: Writing[] };

const WritingTable = ({ writings }: Props) => {
  const { goWritingPage } = usePageNavigate();

  const getPublishedToLogoIcon = (blogName: Blog) => {
    switch (blogName) {
      case 'MEDIUM':
        return <MediumLogoIcon width='2.4rem' height='2.4rem' />;
      case 'TISTORY':
        return <TistoryLogoIcon width='2.4rem' height='2.4rem' />;
    }
  };

  return (
    <>
      <S.WritingTableContainer>
        <thead>
          <tr>
            <th>Title</th>
            <th>Published To</th>
            <th>Published Time</th>
          </tr>
        </thead>
        <tbody>
          {writings.map((writing) => (
            <tr key={writing.id} onClick={() => goWritingPage(writing.id)}>
              <td>{writing.title}</td>
              <td>
                <div className='publishedTo'>
                  {writing.publishedDetails.map((publishedDetail) => (
                    <Fragment key={publishedDetail.blogName}>
                      {getPublishedToLogoIcon(publishedDetail.blogName)}
                    </Fragment>
                  ))}
                </div>
              </td>
              <td>{dateFormatter(writing.createdAt, 'YYYY.MM.DD.')}</td>
            </tr>
          ))}
        </tbody>
      </S.WritingTableContainer>
    </>
  );
};

export default WritingTable;

const S = {
  WritingTableContainer: styled.table`
    width: 100%;
    text-align: left;
    font-size: 1.4rem;

    th {
      color: ${({ theme }) => theme.color.gray7};
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

    tr:not(:first-child):hover {
      cursor: pointer;
      transform: scale(1.01);
      transition: all 300ms;
    }
  `,
};
