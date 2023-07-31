import { MediumLogoIcon, TistoryLogoIcon } from 'assets/icons';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { Fragment, ReactElement } from 'react';
import { styled } from 'styled-components';
import { Writing } from 'types/apis/writings';
import { Blog } from 'types/domain';
import { dateFormatter } from 'utils/date';

type Props = { writings: Writing[] };

const blogIcon: Record<Blog, ReactElement> = {
  MEDIUM: <MediumLogoIcon width='2.4rem' height='2.4rem' />,
  TISTORY: <TistoryLogoIcon width='2.4rem' height='2.4rem' />,
};

const WritingTable = ({ writings }: Props) => {
  const { goWritingPage } = usePageNavigate();

  return (
    <S.WritingTableContainer>
      <colgroup>
        <col width='60%' />
        <col width='20%' />
        <col width='20%' />
      </colgroup>
      <thead>
        <tr>
          <th>Title</th>
          <th>Published To</th>
          <th>Published Time</th>
        </tr>
      </thead>
      <tbody>
        {writings.map(({ id, title, publishedDetails, createdAt }) => (
          <tr key={id} onClick={() => goWritingPage(id)}>
            <td>{title}</td>
            <td>
              <S.PublishedToIconContainer>
                {publishedDetails.map(({ blogName }) => (
                  <Fragment key={blogName}>{blogIcon[blogName]}</Fragment>
                ))}
              </S.PublishedToIconContainer>
            </td>
            <td>{dateFormatter(createdAt, 'YYYY.MM.DD.')}</td>
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

    tbody tr:hover {
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
