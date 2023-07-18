import { useState } from 'react';
import { styled } from 'styled-components';
import { publishWriting } from 'apis/writings';
import { CheckSymbol } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import Spinner from 'components/@common/Spinner/Spinner';
import useMutation from 'hooks/@common/useMutation';
import { PublishToArg, PublishWritingArgs } from 'types/apis/writings';

type Props = {
  name: PublishToArg;
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

  const publishWritingToBlog = async (name: PublishToArg) => {
    const body = {
      publishTo: name,
    };

    await mutateQuery({ writingId, body });
  };

  return (
    <S.BlogPublishButtonItem key={name} $isLoading={isLoading}>
      <Button
        size='large'
        block={true}
        align='left'
        disabled={isLoading || isSucceedPublish}
        onClick={() => publishWritingToBlog(name)}
      >
        {name}
      </Button>
      <S.PublishStateWrapper>
        {isLoading ? <Spinner /> : <></>}
        {isSucceedPublish ? <CheckSymbol width='2.4rem' height='2.4rem' /> : <></>}
      </S.PublishStateWrapper>
    </S.BlogPublishButtonItem>
  );
};

export default BlogPublishButtonItem;

const S = {
  BlogPublishButtonItem: styled.li<Record<'$isLoading', boolean>>`
    position: relative;

    & > button {
      background-color: ${({ $isLoading, theme }) =>
        $isLoading ? theme.color.primary : theme.color.gray1};
      color: ${({ $isLoading, theme }) => ($isLoading ? theme.color.gray2 : theme.color.gray10)};

      cursor: ${({ $isLoading }) => ($isLoading ? 'default' : 'pointer')};
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
