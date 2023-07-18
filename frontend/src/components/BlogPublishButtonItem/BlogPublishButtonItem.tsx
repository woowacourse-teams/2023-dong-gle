import { useState } from 'react';
import { styled } from 'styled-components';
import { publishWriting } from 'apis/writings';
import { CheckSymbol } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import Spinner from 'components/@common/Spinner/Spinner';
import useMutation from 'hooks/@common/useMutation';
import type { PublishWritingArgs } from 'types/apis/writings';
import { Blog } from 'types/domain';

type Props = {
  name: Blog;
  writingId: number;
  isPublished: boolean;
};

// TODO: 글정보 GET 연결하면 isSucceedPublish를 isPublished로 치환
const BlogPublishButtonItem = ({ name, writingId, isPublished }: Props) => {
  const [isSucceedPublish, setIsSucceedPublish] = useState(false);
  const { mutateQuery, isLoading } = useMutation<PublishWritingArgs, null>({
    fetcher: publishWriting,
    onSuccess: () => setIsSucceedPublish(true),
    onError: () => setIsSucceedPublish(false),
  });

  const publishWritingToBlog = (name: Blog) => async () => {
    const body = {
      publishTo: name,
    };

    await mutateQuery({ writingId, body });
  };

  return (
    <S.BlogPublishButtonItem key={name} $isLoading={isLoading} $isSucceedPublish={isSucceedPublish}>
      <Button
        size='large'
        block
        align='left'
        disabled={isLoading || isSucceedPublish}
        onClick={publishWritingToBlog(name)}
      >
        {name}
      </Button>
      <S.PublishStateWrapper>
        {isLoading && <Spinner />}
        {isSucceedPublish && <CheckSymbol width='2.4rem' height='2.4rem' />}
      </S.PublishStateWrapper>
    </S.BlogPublishButtonItem>
  );
};

export default BlogPublishButtonItem;

const S = {
  BlogPublishButtonItem: styled.li<{ $isLoading: boolean; $isSucceedPublish: boolean }>`
    position: relative;

    & > button {
      background-color: ${({ $isLoading, theme }) =>
        $isLoading ? theme.color.primary : theme.color.gray1};
      color: ${({ $isLoading, theme }) => ($isLoading ? theme.color.gray2 : theme.color.gray10)};

      cursor: ${({ $isLoading, $isSucceedPublish }) =>
        $isLoading || $isSucceedPublish ? 'auto' : 'pointer'};
    }
  `,

  PublishStateWrapper: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 50%;
    right: 0.8rem;
    transform: translateY(-50%);
    width: 4.8rem;
    height: 4.8rem;
  `,
};
