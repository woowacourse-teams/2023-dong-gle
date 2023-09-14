import { Fragment } from 'react';
import { getWritingProperties } from 'apis/writings';
import { CalendarIcon, HyperlinkIcon, TagIcon } from 'assets/icons';
import Tag from 'components/@common/Tag/Tag';
import { styled } from 'styled-components';
import { dateFormatter } from 'utils/date';
import { useQuery } from '@tanstack/react-query';
import { BLOG_ICON, BLOG_KOREAN } from 'constants/blog';

type Props = {
  writingId: number;
};

const WritingPropertySection = ({ writingId }: Props) => {
  const { data: writingInfo } = useQuery(['writingProperties', writingId], () =>
    getWritingProperties(writingId),
  );

  if (!writingInfo) return null;

  return (
    <S.WritingPropertySection>
      <S.SectionTitle>정보</S.SectionTitle>
      <S.InfoList>
        <S.Info>
          <S.InfoTitle>글 정보</S.InfoTitle>
          <S.InfoContent>
            <S.PropertyRow>
              <S.PropertyName>
                <CalendarIcon width={12} height={12} />
                생성 날짜
              </S.PropertyName>
              <S.PropertyValue>
                {dateFormatter(writingInfo.createdAt, 'YYYY/MM/DD HH:MM')}
              </S.PropertyValue>
            </S.PropertyRow>
          </S.InfoContent>
        </S.Info>
        {Boolean(writingInfo.publishedDetails.length) && (
          <S.Info>
            <S.InfoTitle>발행 정보</S.InfoTitle>
            <S.InfoContent>
              {writingInfo.publishedDetails.map(
                ({ blogName, publishedAt, tags, publishedUrl }, index) => {
                  return (
                    <Fragment key={index}>
                      <S.PropertyRow>
                        <S.PropertyName>
                          {BLOG_ICON[blogName]} {BLOG_KOREAN[blogName]}
                        </S.PropertyName>
                      </S.PropertyRow>
                      {publishedUrl ? (
                        <S.PropertyRow>
                          <S.PropertyName>
                            <HyperlinkIcon width={10} height={10} />
                            발행 링크
                          </S.PropertyName>
                          <S.PropertyValue>
                            <S.BlogLink href={publishedUrl} target='_blank' rel='external'>
                              블로그로 이동하기
                            </S.BlogLink>
                          </S.PropertyValue>
                        </S.PropertyRow>
                      ) : null}
                      <S.PropertyRow>
                        <S.PropertyName>
                          <CalendarIcon width={12} height={12} />
                          발행일
                        </S.PropertyName>
                        <S.PropertyValue>
                          {dateFormatter(publishedAt, 'YYYY/MM/DD HH:MM')}
                        </S.PropertyValue>
                      </S.PropertyRow>
                      <S.PropertyRow>
                        <S.PropertyName>
                          <TagIcon width={12} height={12} />
                          태그
                        </S.PropertyName>
                        <S.PropertyValue>
                          {tags.map((tag) => (
                            <Tag key={tag} removable={false}>
                              {tag}
                            </Tag>
                          ))}
                        </S.PropertyValue>
                      </S.PropertyRow>
                      <S.Spacer />
                    </Fragment>
                  );
                },
              )}
            </S.InfoContent>
          </S.Info>
        )}
      </S.InfoList>
    </S.WritingPropertySection>
  );
};

export default WritingPropertySection;

const S = {
  WritingPropertySection: styled.section`
    display: flex;
    flex-direction: column;
    gap: 2rem;
  `,
  SectionTitle: styled.h1`
    display: flex;
    gap: 1.5rem;
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.5rem;
  `,
  InfoList: styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,
  Info: styled.div`
    color: ${({ theme }) => theme.color.gray7};
  `,
  InfoTitle: styled.h2`
    color: ${({ theme }) => theme.color.gray8};
    font-size: 1.3rem;
    font-weight: 600;
    line-height: 1.3rem;
  `,
  InfoContent: styled.div`
    display: flex;
    flex-direction: column;
    gap: 0.8rem;
    padding: 1.6rem 0.9rem;
    font-size: 1.3rem;
    line-height: 1.3rem;
  `,
  PropertyRow: styled.div`
    display: flex;
    align-items: center;
  `,
  PropertyName: styled.div`
    display: flex;
    align-items: center;
    align-self: flex-start;
    flex-shrink: 0;
    gap: 0.4rem;
    width: 9.5rem;
    height: 2.3rem;
    color: ${({ theme }) => theme.color.gray8};
    font-size: 1.3rem;
    font-weight: 600;

    svg {
      width: 1.2rem;
      height: 1.2rem;
    }
  `,
  PropertyValue: styled.div`
    color: ${({ theme }) => theme.color.gray10};
    display: flex;
    flex-wrap: wrap;
    gap: 0.2rem;
  `,
  Spacer: styled.div`
    height: 0.8rem;
  `,

  BlogLink: styled.a`
    color: ${({ theme }) => theme.color.gray12};
    font-weight: 500;
  `,
};
