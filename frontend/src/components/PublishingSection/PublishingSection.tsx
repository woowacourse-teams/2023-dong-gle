import { styled } from 'styled-components';
import { BLOG_LIST } from 'constants/blog';
import type { Blog } from 'types/domain';
import Button from 'components/@common/Button/Button';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';

type Props = {
  onBlogButtonClick: (blog: Blog) => void;
  onTabClick: (tabKey: TabKeys) => void;
};

const PublishingSection = ({ onTabClick, onBlogButtonClick }: Props) => {
  const openPublishingPropertySection = (blog: Blog) => {
    onBlogButtonClick(blog);
    onTabClick(TabKeys.PublishingProperty);
  };

  return (
    <S.PublishingSection>
      <S.PublishingTitle>Publish</S.PublishingTitle>
      <S.BlogPublishButtonList>
        {Object.values(BLOG_LIST).map((name) => {
          return (
            <Button
              key={name}
              size='medium'
              block
              align='left'
              onClick={() => openPublishingPropertySection(name)}
            >
              {name}
            </Button>
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
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.5rem;
  `,

  BlogPublishButtonList: styled.ul`
    display: flex;
    flex-direction: column;
    gap: 1.6rem;
  `,
};
