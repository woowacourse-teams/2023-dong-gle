import BlogList from 'components/BlogList/BlogList';
import { styled } from 'styled-components';

const PublishingSection = () => {
  return (
    <S.PublishingSection>
      <S.PublishingTitle>Publish</S.PublishingTitle>
      <BlogList />
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
