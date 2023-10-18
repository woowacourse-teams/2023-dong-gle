import DOMPurify from 'dompurify';
import { css, styled } from 'styled-components';
import { getWriting } from 'apis/writings';
import Divider from 'components/@common/Divider/Divider';
import Spinner from 'components/@common/Spinner/Spinner';
import { useQuery } from '@tanstack/react-query';
import WritingTitle from './WritingTitle/WritingTitle';
import useCodeHighlight from 'hooks/@common/useCodeHighlight';
import { MAX_WIDTH } from 'constants/style';

type Props = {
  writingId: number;
  categoryId: number;
  isDeletedWriting?: boolean;
};

const WritingViewer = ({ writingId, categoryId, isDeletedWriting }: Props) => {
  const { data, isLoading } = useQuery(['writings', writingId], () => getWriting(writingId));
  useCodeHighlight(data?.content);

  if (isLoading) {
    return (
      <S.LoadingContainer>
        <Spinner size={60} thickness={4} />
        <h1>글을 불러오는 중입니다 ...</h1>
      </S.LoadingContainer>
    );
  }

  return (
    <S.WritingViewerContainer>
      <WritingTitle
        categoryId={categoryId}
        writingId={writingId}
        title={data?.title ?? ''}
        canEditTitle={!isDeletedWriting}
      />
      <Divider />
      <S.ContentWrapper
        tabIndex={0}
        dangerouslySetInnerHTML={{
          __html: DOMPurify.sanitize(data?.content ?? '글 내용이 없습니다'),
        }}
      />
    </S.WritingViewerContainer>
  );
};

export default WritingViewer;

const generateResponsiveStyle = {
  writingViewerContainer: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.laptop}) {
        padding: 4rem 4rem;
      }

      @media (max-width: ${MAX_WIDTH.tablet}) {
        padding: 4rem 2.4rem;
      }
    `;
  },
};

const S = {
  WritingViewerContainer: styled.section`
    padding: 8rem;
    width: 100%;
    overflow-wrap: break-word;
    background-color: ${({ theme }) => theme.color.gray1};

    ${() => generateResponsiveStyle.writingViewerContainer()}
  `,

  LoadingContainer: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 2rem;
    max-width: 100%;
    height: 100%;
  `,

  ContentWrapper: styled.section`
    padding: 1.6rem 0;
    font-size: 1.6rem;

    h1 {
      padding: 3.4rem 0 1.7rem;
      font-size: 3.4rem;
    }

    h2 {
      padding: 2.8rem 0 1.4rem;
      font-size: 2.8rem;
    }

    h3 {
      padding: 2.2rem 0 1.1rem;
      font-size: 2.2rem;
    }

    h4 {
      padding: 1.6rem 0 0.8rem;
      font-size: 1.6rem;
    }

    h5 {
      padding: 1.3rem 0 0.65rem;
      font-size: 1.3rem;
    }

    h6 {
      padding: 1rem 0 0.5rem;
      font-size: 1rem;
    }

    p {
      padding: 1rem 0;
      font-size: 1.6rem;
      line-height: 2.3rem;
    }

    blockquote {
      padding: 1rem 2rem;
      margin: 1.6rem 0;
      border-left: 4px solid ${({ theme }) => theme.color.gray8};
      background-color: ${({ theme }) => theme.color.gray2};
      color: ${({ theme }) => theme.color.gray9};
      line-height: 2.4rem;
    }

    ol,
    ul {
      padding-left: 2rem;
    }

    ul > li {
      list-style: disc;
    }

    ol > li {
      list-style: decimal;
    }

    li {
      padding: 0.5rem 0;
    }

    a {
      color: #0968da;
      text-decoration: underline;
      &:visited {
        color: #0968da;
      }
    }

    code {
      padding: 0.2rem 0.4rem;
      margin: 0.1rem;
      border: none solid #eee;
      border-radius: 4px;
      background-color: ${({ theme }) => theme.color.gray4};
      color: #d71919;
    }

    pre > code {
      color: inherit;
      background-color: transparent;
    }

    img {
      max-width: 100%;
      height: auto;
    }

    strong {
      font-weight: bold;
    }

    em {
      font-style: italic;
    }
  `,
};
