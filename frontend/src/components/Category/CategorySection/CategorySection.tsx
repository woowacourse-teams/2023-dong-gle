import Accordion from 'components/@common/Accordion/Accordion';
import { styled } from 'styled-components';
import { useCategoryWritings } from 'components/Category/CategorySection/useCategoryWritings';
import { PlusCircleIcon } from 'assets/icons';
import { KeyboardEventHandler, useState } from 'react';
import useCategoryInput from '../useCategoryInput';
import Category from '../Category/Category';
import WritingList from '../WritingList/WritingList';
import { useCategoryDetails } from './useCategoryDetails';
import { useCategoryMutation } from '../useCategoryMutation';

const CategorySection = () => {
  const [selectedCategoryId, setSelectedCategoryId] = useState(0);
  const { addCategory } = useCategoryMutation();
  const writings = useCategoryWritings(selectedCategoryId);
  const { categoryDetails, getCategories } = useCategoryDetails(selectedCategoryId, writings);
  const {
    value,
    inputRef,
    handleOnChange,
    escapeInput: escapeAddCategory,
    isInputOpen,
    setIsInputOpen,
    closeInput,
  } = useCategoryInput('');

  const requestAddCategory: KeyboardEventHandler<HTMLInputElement> = async (e) => {
    if (e.key !== 'Enter') return;

    closeInput();
    await addCategory({ categoryName: value });
    await getCategories();
  };

  if (!categoryDetails) return null;

  return (
    <S.Section>
      <S.Header>
        <S.Title>Folders</S.Title>
        {isInputOpen ? (
          <S.Input
            type='text'
            value={value}
            ref={inputRef}
            onBlur={closeInput}
            onChange={handleOnChange}
            onKeyDown={escapeAddCategory}
            onKeyUp={requestAddCategory}
            placeholder='New category name'
          />
        ) : (
          <S.Button onClick={() => setIsInputOpen(true)}>
            <PlusCircleIcon width={12} height={12} />
          </S.Button>
        )}
      </S.Header>
      <Accordion>
        {categoryDetails.map((categoryDetail, index) => {
          return (
            <Accordion.Item key={categoryDetail.id}>
              <Accordion.Title onIconClick={() => setSelectedCategoryId(categoryDetail.id)}>
                <Category
                  id={categoryDetail.id}
                  categoryName={categoryDetail.categoryName}
                  getCategories={getCategories}
                  isDefaultCategory={index === 0}
                />
              </Accordion.Title>
              <Accordion.Panel>
                {categoryDetail.writings && <WritingList writings={categoryDetail.writings} />}
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
    height: 2.8rem;
    font-size: 1.2rem;
    font-weight: 400;
    padding-right: 0.8rem;
  `,

  Button: styled.button`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2.4rem;
    border-radius: 8px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,

  Title: styled.h1`
    color: ${({ theme }) => theme.color.gray7};
    cursor: default;
  `,

  Input: styled.input`
    border: none;
    outline: none;
    color: ${({ theme }) => theme.color.gray10};
    font-size: 1.2rem;
    font-weight: 600;

    &::placeholder {
      font-weight: 300;
    }
  `,
};
