import Accordion from 'components/@common/Accordion/Accordion';
import { useCategories } from 'hooks/useCategories';
import { styled } from 'styled-components';
import Category from './Category';
import { useCategoryDetail } from 'hooks/useCategoryDetail';
import Button from 'components/@common/Button/Button';
import { PlusCircleIcon } from 'assets/icons';
import { KeyboardEvent } from 'react';
import WritingList from './WritingList';
import useCategoryInput from './useCategoryInput';
import { useAddCategory } from './useAddCategory';

// TODO
// [카테고리,글목록] 튜플 빌드 훅 작성
// 토글 off시 데이터 지우기

const CategorySection = () => {
  const { categories } = useCategories();
  const { categoryId, writings, getWritings } = useCategoryDetail();
  const { addCategory } = useAddCategory();
  const {
    value,
    resetValue,
    inputRef,
    handleOnChange,
    escapeInput: escapeAddCategory,
    isOpenInput,
    setIsOpenInput,
  } = useCategoryInput('');

  const requestAddCategory = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    addCategory(value);
    setIsOpenInput(false);
    resetValue();
  };

  if (!categories) return null;

  return (
    <S.Section>
      <S.Header>
        <p>카테고리</p>
        {isOpenInput ? (
          <S.Input
            type='text'
            value={value}
            ref={inputRef}
            onChange={handleOnChange}
            onKeyDown={escapeAddCategory}
            onKeyUp={requestAddCategory}
          />
        ) : (
          <Button variant='text' size='small' onClick={() => setIsOpenInput(true)}>
            <PlusCircleIcon width={12} height={12} />
          </Button>
        )}
      </S.Header>
      <Accordion>
        {categories.map((category) => {
          return (
            <Accordion.Item>
              <Accordion.Title onIconClick={() => getWritings(category.id)}>
                <Category id={category.id} categoryName={category.categoryName} />
              </Accordion.Title>
              <Accordion.Panel>
                {writings && categoryId === category.id && <WritingList writingList={writings} />}
              </Accordion.Panel>
            </Accordion.Item>
          );
        })}
      </Accordion>
    </S.Section>
  );
};

export default CategorySection;

const S = {
  Section: styled.section`
    width: 26rem;
  `,

  Header: styled.header`
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 1.2rem;
    font-weight: 400;
  `,

  Input: styled.input``,
};
