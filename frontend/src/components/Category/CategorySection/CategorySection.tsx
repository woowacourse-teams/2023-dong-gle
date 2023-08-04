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
import Input from 'components/@common/Input/Input';
import { isValidCategoryName } from '../isValidCategoryName';

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
    resetInput,
    isError,
    setIsError,
  } = useCategoryInput('');

  const requestAddCategory: KeyboardEventHandler<HTMLInputElement> = async (e) => {
    if (e.key !== 'Enter') return;

    if (!isValidCategoryName(value)) {
      setIsError(true);
      return;
    }

    resetInput();
    await addCategory({ categoryName: value.trim() });
    await getCategories();
  };

  if (!categoryDetails) return null;

  return (
    <S.Section>
      <S.Header>
        <S.Title>Folders</S.Title>
        {isInputOpen ? (
          <Input
            type='text'
            variant='underlined'
            size='small'
            placeholder='Add category ...'
            value={value}
            ref={inputRef}
            isError={isError}
            onBlur={resetInput}
            onChange={handleOnChange}
            onKeyDown={escapeAddCategory}
            onKeyUp={requestAddCategory}
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
                  isDefaultCategory={index === 0}
                  getCategories={getCategories}
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
