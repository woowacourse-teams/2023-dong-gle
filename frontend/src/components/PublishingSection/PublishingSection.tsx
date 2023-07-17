import BlogPublishButtonList from 'components/BlogPublishButtonList/BlogPublishButtonList';
import { styled } from 'styled-components';

type PublishingSectionProps = {
  writingId: number;
};

const PublishingSection = ({ writingId }: PublishingSectionProps) => {
  return (
    <S.PublishingSection>
      <S.PublishingTitle>Publish</S.PublishingTitle>
      <BlogPublishButtonList writingId={writingId} />
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
