import { styled } from 'styled-components';
import { BLOG_ICON, BLOG_KOREAN, BLOG_LIST } from 'constants/blog';
import type { Blog } from 'types/domain';
import Button from 'components/@common/Button/Button';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';
import { useMember } from 'hooks/queries/useMember';
import { usePageNavigate } from 'hooks/usePageNavigate';

type Props = {
  onBlogButtonClick: (blog: Blog) => void;
  onTabClick: (tabKey: TabKeys) => void;
};

const PublishingSection = ({ onTabClick, onBlogButtonClick }: Props) => {
  const { tistory, medium } = useMember();
  const { goMyPage } = usePageNavigate();

  const 블로그가하나라도연결되었는지 = tistory?.isConnected || medium?.isConnected;
  const 모든블로그가연결되었는지 = tistory?.isConnected && medium?.isConnected;

  const openPublishingPropertySection = (blog: Blog) => {
    onBlogButtonClick(blog);

    switch (blog) {
      case 'MEDIUM':
        onTabClick(TabKeys.MediumPublishingProperty);
        break;

      case 'TISTORY':
        onTabClick(TabKeys.TistoryPublishingProperty);
        break;
    }
  };

  return (
    <S.PublishingSection>
      <S.PublishingTitle>발행하기</S.PublishingTitle>
      {블로그가하나라도연결되었는지 ? (
        <>
          <S.BlogPublishButtonList>
            {Object.values(BLOG_LIST).map((name) => {
              // 연결 여부 확인을 위한 로직 추가
              const shouldRenderButton =
                (name === 'TISTORY' && tistory?.isConnected) ||
                (name === 'MEDIUM' && medium?.isConnected);

              // 연결이 확인되지 않으면, 버튼을 렌더링하지 않는다.
              if (!shouldRenderButton) {
                return null;
              }
              // 연결 안된 게 있으면 "다른 블로그 연결하기" 렌더링

              return (
                <Button
                  key={name}
                  size='medium'
                  block
                  align='left'
                  icon={BLOG_ICON[name]}
                  onClick={() => openPublishingPropertySection(name)}
                >
                  {BLOG_KOREAN[name]}
                </Button>
              );
            })}
          </S.BlogPublishButtonList>
          {!모든블로그가연결되었는지 && (
            <S.AddBlogConnectionButton onClick={goMyPage}>
              블로그 추가로 연결하기
            </S.AddBlogConnectionButton>
          )}
        </>
      ) : (
        <S.AddFirstBlogConnectionContainer>
          <S.AddFirstBlogConnectionText>
            블로그를 연결해서 글을 발행해보세요!
          </S.AddFirstBlogConnectionText>
          <Button variant='secondary' onClick={goMyPage}>
            연결 하러가기
          </Button>
        </S.AddFirstBlogConnectionContainer>
      )}
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

  ButtonContent: styled.div`
    display: flex;
  `,

  AddBlogConnectionButton: styled.button`
    color: ${({ theme }) => theme.color.gray7};

    &:hover {
      color: ${({ theme }) => theme.color.gray9};
    }

    &:active {
      color: ${({ theme }) => theme.color.gray7};
    }
  `,

  AddFirstBlogConnectionContainer: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 2rem;
  `,

  AddFirstBlogConnectionText: styled.p`
    font-size: 1.5rem;
  `,
};
