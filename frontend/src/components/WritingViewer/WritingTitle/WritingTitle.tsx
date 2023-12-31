import { useMutation, useQueryClient } from '@tanstack/react-query';
import { css, styled } from 'styled-components';
import { PencilIcon } from 'assets/icons';
import { updateWritingTitle as updateWritingTitleRequest } from 'apis/writings';
import { ChangeEvent, KeyboardEventHandler, useEffect, useRef } from 'react';
import { getErrorMessage } from 'utils/error';
import { useToast } from 'hooks/@common/useToast';
import { validateWritingTitle } from 'utils/validators';
import { GetWritingResponse } from 'types/apis/writings';
import { GetCategoryDetailResponse } from 'types/apis/category';
import useControlledInput from 'hooks/@common/useControlledInput';

type Props = {
  writingId: number;
  categoryId: number;
  title: string;
  canEditTitle?: boolean;
};

const WritingTitle = ({ writingId, categoryId, title, canEditTitle = true }: Props) => {
  const {
    value: inputTitle,
    setValue: setInputTitle,
    inputRef,
    escapeInput: escapeRename,
    isInputOpen,
    openInput,
    resetInput,
  } = useControlledInput(title);
  const myRef = useRef<HTMLHeadingElement>(null);
  const queryClient = useQueryClient();
  const toast = useToast();

  const { mutate: updateWritingTitle } = useMutation(updateWritingTitleRequest, {
    onMutate: async ({ writingId, body: { title } }) => {
      await queryClient.cancelQueries(['writings', writingId]);
      await queryClient.cancelQueries(['writingsInCategory', categoryId]);

      const previousWritings = queryClient.getQueryData<GetWritingResponse>([
        'writings',
        writingId,
      ]);
      const previousWritingsInCategory = queryClient.getQueryData<GetCategoryDetailResponse>([
        'writingsInCategory',
        categoryId,
      ]);

      previousWritings &&
        queryClient.setQueryData(['writings', writingId], (old: any) => {
          return { ...old, title };
        });

      previousWritingsInCategory &&
        queryClient.setQueryData(['writingsInCategory', categoryId], (old: any) => {
          return {
            ...old,
            writings: old.writings.map((writing: any) => {
              return writing.id === writingId ? { id: writing.id, title } : writing;
            }),
          };
        });

      return { previousWritings, previousWritingsInCategory };
    },
    onError: (error, _, context) => {
      setInputTitle(context?.previousWritings?.title || '');
      queryClient.setQueryData(['writings', writingId], context?.previousWritings);
      queryClient.setQueryData(
        ['writingsInCategory', categoryId],
        context?.previousWritingsInCategory,
      );
      toast.show({ type: 'error', message: '글 제목 수정에 실패했습니다.' });
    },
    onSettled: () => {
      queryClient.invalidateQueries(['writings', writingId]);
      queryClient.invalidateQueries(['writingsInCategory', categoryId]);
    },
  });

  const requestChangedName: KeyboardEventHandler<HTMLInputElement> = (e) => {
    try {
      if (e.key !== 'Enter') return;

      const writingTitle = e.currentTarget.value.trim();

      validateWritingTitle(writingTitle);

      resetInput();

      updateWritingTitle({
        writingId,
        body: {
          title: writingTitle,
        },
      });
    } catch (error) {
      toast.show({ type: 'error', message: getErrorMessage(error) });
    }
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
          defaultValue={title}
          value={inputTitle}
          ref={inputRef}
          onChange={(e: ChangeEvent<HTMLInputElement>) => setInputTitle(e.target.value)}
          onBlur={resetInput}
          onKeyDown={escapeRename}
          onKeyUp={requestChangedName}
        />
      ) : (
        <>
          <S.Title ref={myRef} tabIndex={0}>
            {inputTitle}
          </S.Title>
          {canEditTitle && (
            <S.Button aria-label={'글 제목 수정'} onClick={openInput}>
              <PencilIcon width={20} height={20} />
            </S.Button>
          )}
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
    gap: 0.3rem;
  `,
  Title: styled.h1`
    font-size: 4rem;
    padding: 0.1rem;
  `,
  Button: styled.button`
    display: flex;
    justify-content: center;
    align-items: center;
    flex-shrink: 0;
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
    ${({ disabled }) => css`
      background-color: ${disabled ? 'initial' : 'desiredColor'};
      color: ${disabled ? 'initial' : 'desiredColor'};
    `}
  `,
};
