import { BLOG } from 'constants/blog';
import { styled } from 'styled-components';

/**
 * TODO: MSW handler
 * rest.post('/writings/{writingId}/publish', async (req, res, ctx) => {
 * 	const { publishTo } = await req.json();
 *
 * 	return res(ctx.status(200));
 * })
 * */

export const publishWritingToBlog = async (publishTo: string, writingId: number) => {
  // TODO: API 요청 로직 추상화
  const url = `/writings/${writingId}/publish`;
  const options = {
    method: 'POST',
    body: JSON.stringify({
      publishTo,
    }),
  };

  const response = await fetch(url, options);
  if (!response.ok) {
    // TODO: 던져주는 에러메세지 UI에 렌더링, 에러 처리 고도화 및 추상화
    throw new Error(response.status.toString());
  }
};

type Props = {
  blogName: (typeof BLOG)[keyof typeof BLOG];
};

const BlogButton = ({ blogName }: Props) => {
  const handleBlogButtonClick = () => {
    // publishWritingToBlog(blogName, 1);
  };

  // TODO: 쿠마 버튼 완성되면 컴포넌트 교체
  return <S.BlogButton onClick={handleBlogButtonClick}>{blogName}</S.BlogButton>;
};

export default BlogButton;

const S = {
  BlogButton: styled.button`
    width: 30rem;
    height: 5rem;
    padding: 0 2rem;
    border-radius: 8px;

    text-align: start;
    font-size: 1.5rem;
    font-weight: 500;
    line-height: 1.8rem;

    &:hover {
      background-color: #102756;
      color: white;
    }
  `,
};
