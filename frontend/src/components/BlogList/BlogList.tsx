import { BLOG } from 'constants/blog';
import BlogButton from 'components/BlogButton/BlogButton';
import { styled } from 'styled-components';

const BlogList = () => {
  return (
    <S.BlogList>
      {Object.values(BLOG).map((name) => {
        return (
          <li key={name}>
            <BlogButton blogName={name} />
          </li>
        );
      })}
    </S.BlogList>
  );
};

export default BlogList;

const S = {
  BlogList: styled.ul`
    display: flex;
    flex-direction: column;
    gap: 1.6rem;
  `,
};
