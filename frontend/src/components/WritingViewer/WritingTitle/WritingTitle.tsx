import { useMutation, useQueryClient } from '@tanstack/react-query';
import { css, styled } from 'styled-components';
import { PencilIcon } from 'assets/icons';
import useCategoryInput from 'components/Category/useCategoryInput';
import Input from 'components/@common/Input/Input';
import { updateWritingTitle as updateWritingTitleRequest } from 'apis/writings';
import { KeyboardEventHandler, useEffect, useRef } from 'react';

type Props = {
  writingId: number;
  categoryId: number;
  title: string;
};

const WritingTitle = ({ writingId, categoryId, title }: Props) => {
  const {
    inputRef,
    escapeInput: escapeRename,
    isInputOpen,
    openInput,
    resetInput,
    isError,
  } = useCategoryInput('');
  const myRef = useRef<HTMLHeadingElement>(null);
  const queryClient = useQueryClient();
  const { mutate: updateWritingTitle } = useMutation(updateWritingTitleRequest, {
    onSuccess: () => {
      queryClient.invalidateQueries(['writings', writingId]);
      queryClient.invalidateQueries(['writingsInCategory', categoryId]);
    },
  });

  const requestChangedName: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key !== 'Enter') return;

    const writingTitle = e.currentTarget.value;

    updateWritingTitle({
      writingId,
      body: {
        title: writingTitle,
      },
    });

    resetInput();
  };

  useEffect(() => {
    myRef.current?.focus();
  }, [writingId]);

  return (
    <S.TitleWrapper>
      {isInputOpen ? (
        <S.Input
          type='text'
          placeholder='새 제목을 입력해주세요'
          ref={inputRef}
          onBlur={resetInput}
          onKeyDown={escapeRename}
          onKeyUp={requestChangedName}
        />
      ) : (
        <>
          <S.Title ref={myRef} tabIndex={0}>
            {title}
          </S.Title>
          <S.Button aria-label={'글 제목 수정'} onClick={openInput}>
            <PencilIcon width={20} height={20} />
          </S.Button>
        </>
      )}
    </S.TitleWrapper>
  );
};

export default WritingTitle;

const S = {
  TitleWrapper: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 2rem;
  `,
  Title: styled.h1`
    font-size: 4rem;
    padding: 0.1rem;
  `,
  Button: styled.button`
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 12px;
    padding: 1rem;
    background-color: ${({ theme }) => theme.color.gray4};

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
  Input: styled.input`
    font-size: 4rem;
    font-weight: 700;
    width: 100%;
    ${({ theme }) => css`
      border: 1px solid ${theme.color.gray1};
      outline: 1px solid ${theme.color.gray1};
    `}
  `,
};