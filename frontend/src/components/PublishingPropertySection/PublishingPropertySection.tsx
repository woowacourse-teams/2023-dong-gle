import { styled } from 'styled-components';
import TagInput from '../@common/TagInput/TagInput';
import Button from '../@common/Button/Button';
import { LeftArrowHeadIcon, TagIcon } from 'assets/icons';
import { slide } from 'styles/animation';

type Props = {
  changeTab: (index: number) => () => void;
};

const PublishingPropertySection = ({ changeTab }: Props) => {
  return (
    <S.PublishingPropertySection>
      <S.SectionHeader>
        {/* TODO: changeTab에 index 상수화 */}
        <button onClick={changeTab(1)}>
          <LeftArrowHeadIcon width={14} height={14} />
        </button>
        Publishing Properties
      </S.SectionHeader>
      <S.Properties>
        <S.PropertyRow>
          <S.PropertyName>
            <TagIcon width={12} height={12} />
            Tags
          </S.PropertyName>
          <div>
            <TagInput />
          </div>
        </S.PropertyRow>
      </S.Properties>
      <Button block variant='secondary'>
        Publish
      </Button>
    </S.PublishingPropertySection>
  );
};

export default PublishingPropertySection;

const S = {
  PublishingPropertySection: styled.section`
    display: flex;
    flex-direction: column;
    gap: 2rem;
    animation: ${slide} 0.5s;
  `,
  SectionHeader: styled.h1`
    display: flex;
    gap: 1.5rem;
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.5rem;
  `,
  Properties: styled.div`
    padding-left: 0.9rem;
  `,
  PropertyRow: styled.div`
    display: flex;
    align-items: flex-start;
  `,
  PropertyName: styled.div`
    display: flex;
    align-items: center;
    flex-shrink: 0;
    width: 9.5rem;
    height: 100%;
    color: ${({ theme }) => theme.color.gray8};
    font-size: 1.3rem;
    font-weight: 600;
  `,
};
