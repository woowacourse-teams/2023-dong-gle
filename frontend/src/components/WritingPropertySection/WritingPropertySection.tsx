import { Fragment, ReactElement } from 'react';
import { getWritingProperties } from 'apis/writings';
import { CalendarIcon, MediumLogoIcon, TagIcon, TistoryLogoIcon } from 'assets/icons';
import Tag from 'components/@common/Tag/Tag';
import { useGetQuery } from 'hooks/@common/useGetQuery';
import { styled } from 'styled-components';
import { GetWritingPropertiesResponse } from 'types/apis/writings';
import { dateFormatter } from 'utils/date';
import type { Blog } from 'types/domain';

const blogIcon: Record<Blog, ReactElement> = {
  MEDIUM: <MediumLogoIcon width='1.2rem' height='1.2rem' />,
  TISTORY: <TistoryLogoIcon width='1.2rem' height='1.2rem' />,
};

type Props = {
  writingId: number;
};

const WritingPropertySection = ({ writingId }: Props) => {
  const { data: writingInfo } = useGetQuery<GetWritingPropertiesResponse>({
    fetcher: () => getWritingProperties(writingId),
  });

  if (!writingInfo) return null;

  return (
    <S.WritingPropertySection>
      <S.SectionTitle>Writing Info</S.SectionTitle>
      <S.InfoList>
        <S.Info>
          <S.InfoTitle>PROPERTIES</S.InfoTitle>
          <S.InfoContent>
            <S.PropertyRow>
              <S.PropertyName>
                <CalendarIcon width={12} height={12} />
                Created
              </S.PropertyName>
              <S.PropertyValue>
                {dateFormatter(writingInfo.createdAt, 'YYYY/MM/DD HH:MM')}
              </S.PropertyValue>
            </S.PropertyRow>
          </S.InfoContent>
        </S.Info>
        {Boolean(writingInfo.publishedDetails.length) && (
          <S.Info>
            <S.InfoTitle>PUBLISHED</S.InfoTitle>
            <S.InfoContent>
              {writingInfo.publishedDetails.map(({ blogName, publishedAt, tags }) => {
                return (
                  <Fragment key={blogName}>
                    <S.PropertyRow>
                      <S.PropertyName>
                        {blogIcon[blogName]} {blogName}
                      </S.PropertyName>
                      <S.PropertyValue>
                        {dateFormatter(publishedAt, 'YYYY/MM/DD HH:MM')}
                      </S.PropertyValue>
                    </S.PropertyRow>
                    <S.PropertyRow>
                      <S.PropertyName>
                        <TagIcon width={12} height={12} />
                        Tags
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
              })}
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
  `,
  PropertyValue: styled.div`
    display: flex;
    flex-wrap: wrap;
    gap: 0.2rem;
  `,
  Spacer: styled.div`
    height: 0.8rem;
  `,
};
