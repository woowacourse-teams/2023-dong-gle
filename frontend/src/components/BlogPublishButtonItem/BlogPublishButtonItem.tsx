import { publishWriting } from 'apis/writings';
import { CheckSymbol } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import Spinner from 'components/@common/Spinner/Spinner';
import useMutation from 'hooks/@common/useMutation';
import { styled } from 'styled-components';
import { PublishWritingArg } from 'types/apis/writings';

type Props = {
  name: string;
  writingId: number;
  isPublished: boolean;
};

const BlogPublishButtonItem = ({ name, writingId, isPublished }: Props) => {
  const { mutateQuery, isLoading } = useMutation<PublishWritingArg, null>({
    fetcher: publishWriting,
  });

  const publishWritingToBlog = async (name: string) => {
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
        disabled={isLoading || isPublished}
        onClick={() => publishWritingToBlog(name)}
      >
        {name}
      </Button>
      <S.PublishStateWrapper>
        {isLoading ? <Spinner /> : <></>}
        {isPublished ? <CheckSymbol width='2.4rem' height='2.4rem' /> : <></>}
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
