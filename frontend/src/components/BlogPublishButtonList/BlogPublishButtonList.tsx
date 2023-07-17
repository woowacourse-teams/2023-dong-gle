import { BLOG } from 'constants/blog';
import { publishWriting } from 'apis/writings';
import Button from 'components/@common/Button/Button';
import { styled } from 'styled-components';
import { PublishWritingRequest } from 'types/apis/writings';

type BlogPublishButtonListProps = {
  writingId: number;
};

const BlogPublishButtonList = ({ writingId }: BlogPublishButtonListProps) => {
  return (
    <S.BlogPublishButtonList>
      {Object.values(BLOG).map((name) => {
        const body: PublishWritingRequest = { publishTo: name };

        return (
          <li key={name}>
            <Button
              size='small'
              block={true}
              align='left'
              onClick={() => publishWriting(writingId, body)}
            >
              {name}
            </Button>
          </li>
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
