import { css, styled } from 'styled-components';
import TagInput from '../@common/TagInput/TagInput';
import Button from '../@common/Button/Button';
import Spinner from 'components/@common/Spinner/Spinner';
import { LeftArrowHeadIcon, TagIcon } from 'assets/icons';
import { slideToLeft } from 'styles/animation';
import { TabKeys } from 'components/WritingSideBar/WritingSideBar';
import { useMediumPublishingPropertySection } from './useMediumPublishingPropertySection';
import { default as S } from './PublishingPropertyStyle';
import type { Blog } from 'types/domain';

type Props = {
  writingId: number;
  publishTo: Blog;
  selectCurrentTab: (tabKey: TabKeys) => void;
};

enum MediumPublishStatus {
  'PUBLIC' = 'Public',
  'PRIVATE' = 'Draft',
  'PROTECT' = 'Unlisted',
}

const MediumPublishStatusList = Object.keys(
  MediumPublishStatus,
) as (keyof typeof MediumPublishStatus)[];

const MediumPublishingPropertySection = ({ writingId, publishTo, selectCurrentTab }: Props) => {
  const { isLoading, setTags, setPublishStatus, publishWritingToMedium } =
    useMediumPublishingPropertySection({
      selectCurrentTab,
    });

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
          <S.PropertyName>발행 방식</S.PropertyName>
          <div>
            <select>
              {MediumPublishStatusList.map((value, index) => (
                <option key={index} value={value} onChange={() => setPublishStatus(value)}>
                  {MediumPublishStatus[value]}
                </option>
              ))}
            </select>
          </div>
        </S.PropertyRow>
        <S.PropertyRow>
          <S.PropertyName>
            <TagIcon width={12} height={12} />
            Tags
          </S.PropertyName>
          <div>
            <TagInput onChangeTags={setTags} />
          </div>
        </S.PropertyRow>
      </S.Properties>
      <Button block variant='secondary' onClick={() => publishWritingToMedium(writingId)}>
        발행하기
      </Button>
    </S.PublishingPropertySection>
  );
};

export default MediumPublishingPropertySection;
