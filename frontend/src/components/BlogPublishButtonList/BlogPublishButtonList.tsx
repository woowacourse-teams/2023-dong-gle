import { BLOG } from 'constants/blog';
import { styled } from 'styled-components';
import BlogPublishButtonItem from 'components/BlogPublishButtonItem/BlogPublishButtonItem';

type Props = {
  writingId: number;
  isPublished: boolean;
};

const BlogPublishButtonList = ({ writingId, isPublished }: Props) => {
  return (
    <S.BlogPublishButtonList>
      {Object.values(BLOG).map((name) => {
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
  );
};

export default BlogPublishButtonList;

const S = {
  BlogPublishButtonList: styled.ul`
    display: flex;
    flex-direction: column;
    gap: 1.6rem;
  `,
};
