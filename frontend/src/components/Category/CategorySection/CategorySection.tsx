import Accordion from 'components/@common/Accordion/Accordion';
import { styled } from 'styled-components';
import { useCategoryWritings } from 'components/Category/CategorySection/useCategoryWritings';
import Button from 'components/@common/Button/Button';
import { PlusCircleIcon } from 'assets/icons';
import { KeyboardEvent, useState } from 'react';
import useCategoryInput from '../useCategoryInput';
import Category from '../Category/Category';
import WritingList from '../WritingList/WritingList';
import { useCategoryDetails } from './useCategoryDetails';
import { useCategoryMutation } from '../useCategoryMutation';

const CategorySection = () => {
  const [categoryId, setCategoryId] = useState<number | null>(null);
  const [openItems, setOpenItems] = useState<Record<number, boolean>>({});
  const { addCategory } = useCategoryMutation();
  const writings = useCategoryWritings(categoryId);
  const { categoryDetails, getCategories } = useCategoryDetails(categoryId, writings);
  const {
    value,
    inputRef,
    handleOnChange,
    escapeInput: escapeAddCategory,
    isOpenInput,
    setIsOpenInput,
    closeInput,
  } = useCategoryInput('');

  const requestAddCategory = async (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    closeInput();
    await addCategory({ categoryName: value });
    await getCategories();
  };

  const toggleItem = (categoryId: number) => {
    if (!openItems[categoryId]) {
      setCategoryId(categoryId);
    }

    setOpenItems((prevOpenItems) => ({
      ...prevOpenItems,
      [categoryId]: !prevOpenItems[categoryId],
    }));
  };

  if (!categoryDetails) return null;

  return (
    <S.Section>
      <S.Header>
        <p>카테고리</p>
        {isOpenInput ? (
          <S.Input
            type='text'
            value={value}
            ref={inputRef}
            onBlur={closeInput}
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
        {categoryDetails.map((categoryDetail) => {
          return (
            <Accordion.Item key={categoryDetail.id}>
              <Accordion.Title onIconClick={() => toggleItem(categoryDetail.id)}>
                <Category id={categoryDetail.id} categoryName={categoryDetail.categoryName} />
              </Accordion.Title>
              <Accordion.Panel>
                {categoryDetail.writings && <WritingList writingList={categoryDetail.writings} />}
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
