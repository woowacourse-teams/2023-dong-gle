import { styled } from 'styled-components';
import BlogPublishButtonItem from 'components/BlogPublishButtonItem/BlogPublishButtonItem';
import { BLOG_LIST } from 'constants/blog';

type Props = {
  writingId: number;
  isPublished: boolean;
};

const PublishingSection = ({ writingId, isPublished }: Props) => {
  return (
    <S.PublishingSection>
      <S.PublishingTitle>Publish</S.PublishingTitle>
      <S.BlogPublishButtonList>
        {Object.values(BLOG_LIST).map((name) => {
          return (
            <BlogPublishButtonItem
              key={name}
              name={name}
              writingId={writingId}
              isPublished={isPublished}
            />
          );
        })}
      </S.BlogPublishButtonList>
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
    font-size: 2.4rem;
    font-weight: 70rem;
    line-height: 1.8rem;
  `,

  BlogPublishButtonList: styled.ul`
    display: flex;
    flex-direction: column;
    gap: 1.6rem;
  `,
};
