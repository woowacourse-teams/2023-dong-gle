import { css, styled } from 'styled-components';
import TagInput from '../@common/TagInput/TagInput';
import Button from '../@common/Button/Button';
import Spinner from 'components/@common/Spinner/Spinner';
import {
  CategoryIcon,
  LeftArrowHeadIcon,
  PasswordIcon,
  PublishIcon,
  TagIcon,
  TimeIcon,
} from 'assets/icons';
import { slideToLeft } from 'styles/animation';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';
import { useTistoryPublishingPropertySection } from './useTistoryPublishingPropertySection';
import { default as S } from './PublishingPropertyStyle';
import type { Blog } from 'types/domain';
import Input from 'components/@common/Input/Input';
import { dateFormatter } from 'utils/date';
import { ChangeEvent, useState } from 'react';
import Divider from 'components/@common/Divider/Divider';
import { useTistoryCategories } from 'hooks/queries/blogs/useTistoryCategories';

type Props = {
  writingId: number;
  publishTo: Blog;
  selectCurrentTab: (tabKey: TabKeys) => void;
};

enum TistoryPublishStatus {
  'PUBLIC' = '공개',
  'PRIVATE' = '비공개',
  'PROTECT' = '보호',
}

const TistoryPublishStatusList = Object.keys(
  TistoryPublishStatus,
) as (keyof typeof TistoryPublishStatus)[];

const TistoryPublishingPropertySection = ({ writingId, publishTo, selectCurrentTab }: Props) => {
  const { categories, isLoading: isCategoryLoading } = useTistoryCategories();
  const {
    isLoading,
    propertyFormInfo,
    setTags,
    setPublishStatus,
    setCategoryId,
    passwordRef,
    dateRef,
    timeRef,
    publishWritingToTistory,
  } = useTistoryPublishingPropertySection({
    selectCurrentTab,
  });
  const [isPublishTimeInputOpen, setIsPublishTimeInputOpen] = useState(false);

  const { publishStatus } = propertyFormInfo;

  const openPublishTimeInput = () => {
    setIsPublishTimeInputOpen(true);
  };

  const closePublishTimeInput = () => {
    setIsPublishTimeInputOpen(false);
  };

  if (isLoading)
    return (
      <S.LoadingWrapper>
        글을 발행하고 있어요
        <Spinner />
      </S.LoadingWrapper>
    );

  return (
    <S.PublishingPropertySection $blog={publishTo}>
      <S.SectionHeader>
        <button
          onClick={() => selectCurrentTab(TabKeys.Publishing)}
          aria-label='발행 블로그 플랫폼 선택란으로 이동'
        >
          <LeftArrowHeadIcon width={14} height={14} />
        </button>
        발행 정보
      </S.SectionHeader>

      <S.Properties>
        <S.PropertyRow>
          <S.PropertyName>
            <PublishIcon width={12} height={12} />
            발행 방식
          </S.PropertyName>
          <div>
            <select
              onChange={(e) =>
                setPublishStatus(e.target.value as keyof typeof TistoryPublishStatus)
              }
            >
              {TistoryPublishStatusList.map((value, index) => (
                <option key={index} value={value}>
                  {TistoryPublishStatus[value]}
                </option>
              ))}
            </select>
          </div>
        </S.PropertyRow>
        <S.PropertyRow>
          <S.PropertyName>
            <CategoryIcon width={12} height={12} />
            카테고리
          </S.PropertyName>
          <div>
            <S.TistoryCategorySelect
              onChange={(e: ChangeEvent<HTMLSelectElement>) => setCategoryId(e.target.value)}
              disabled={isCategoryLoading}
            >
              {isCategoryLoading && <option hidden>불러오는 중입니다</option>}
              {categories && (
                <>
                  {/* 티스토리 "카테고리 없음" 카테고리의 id가 "0"임 */}
                  <option key='0' value='0'>
                    카테고리 없음
                  </option>
                  {categories.map(({ id, name }) => (
                    <option key={id} value={id}>
                      {name}
                    </option>
                  ))}
                </>
              )}
            </S.TistoryCategorySelect>
          </div>
        </S.PropertyRow>
        {publishStatus === 'PROTECT' && (
          <S.PropertyRow>
            <S.PropertyName>
              <PasswordIcon width={12} height={12} />
              비밀번호
            </S.PropertyName>
            <div>
              <Input variant='underlined' type='password' ref={passwordRef} />
            </div>
          </S.PropertyRow>
        )}
        <S.PropertyRow>
          <S.PropertyName style={{ alignSelf: 'flex-start' }}>
            <TimeIcon width={12} height={12} />
            발행 시간
          </S.PropertyName>
          <S.PublishButtonAndTimeInputContainer>
            <S.PublishButtonContainer>
              <S.PublishButton onClick={closePublishTimeInput} selected={!isPublishTimeInputOpen}>
                현재
              </S.PublishButton>
              <Divider direction='vertical' length='1.2rem' />
              <S.PublishButton onClick={openPublishTimeInput} selected={isPublishTimeInputOpen}>
                예약
              </S.PublishButton>
            </S.PublishButtonContainer>
            {isPublishTimeInputOpen && (
              <S.PublishTimeInputContainer>
                <Input
                  ref={dateRef}
                  type='date'
                  min={dateFormatter(new Date(), 'YYYY-MM-DD')}
                  defaultValue={dateFormatter(new Date(), 'YYYY-MM-DD')}
                />
                <Input
                  ref={timeRef}
                  type='time'
                  defaultValue={dateFormatter(new Date(), 'HH:MM')}
                />
              </S.PublishTimeInputContainer>
            )}
          </S.PublishButtonAndTimeInputContainer>
        </S.PropertyRow>
        <S.PropertyRow>
          <S.PropertyName>
            <TagIcon width={12} height={12} />
            태그
          </S.PropertyName>
          <div>
            <TagInput onChangeTags={setTags} />
          </div>
        </S.PropertyRow>
      </S.Properties>
      <Button block variant='secondary' onClick={() => publishWritingToTistory(writingId)}>
        발행하기
      </Button>
    </S.PublishingPropertySection>
  );
};

export default TistoryPublishingPropertySection;
