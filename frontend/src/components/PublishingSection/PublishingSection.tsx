import BlogPublishButtonList from 'components/BlogPublishButtonList/BlogPublishButtonList';
import { styled } from 'styled-components';

type Props = {
  writingId: number;
  isPublished: boolean;
};

const PublishingSection = ({ writingId, isPublished }: Props) => {
  return (
    <S.PublishingSection>
      <S.PublishingTitle>Publish</S.PublishingTitle>
      <BlogPublishButtonList writingId={writingId} isPublished={isPublished} />
    </S.PublishingSection>
  );
};

export default PublishingSection;

const S = {
  PublishingSection: styled.section`
    display: flex;
    flex-direction: column;
    gap: 2rem;
  `,

  PublishingTitle: styled.h1`
    font-size: 1.6rem;
    font-weight: 70rem;
    line-height: 1.8rem;
  `,
};
