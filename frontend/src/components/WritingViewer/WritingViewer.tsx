import DOMPurify from 'dompurify';
import { styled } from 'styled-components';
import { getWriting } from 'apis/writings';
import { useGetQuery } from 'hooks/@common/useGetQuery';
import { GetWritingResponse } from 'types/apis/writings';

type Props = { writingId: number };

const WritingViewer = ({ writingId }: Props) => {
  const { data, isLoading } = useGetQuery<GetWritingResponse>({
    fetcher: () => getWriting(writingId),
  });

  if (isLoading) return <div>로딩 중...</div>;

  return (
    <S.WritingViewerContainer>
      <S.TitleWrapper>
        <S.Title>{data?.title}</S.Title>
      </S.TitleWrapper>
      <S.ContentWrapper
        dangerouslySetInnerHTML={{
          __html: DOMPurify.sanitize(data?.content ?? '글 내용이 없습니다'),
        }}
      />
    </S.WritingViewerContainer>
  );
};

export default WritingViewer;

const S = {
  WritingViewerContainer: styled.section``,
  TitleWrapper: styled.div``,
  Title: styled.h1``,
  ContentWrapper: styled.section`
    // TODO: 태그 별 스타일 지정
    // TODO: <code> 스타일은 highlight.js 고려
  `,
};
