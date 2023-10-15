import { useMutation, useQueryClient } from '@tanstack/react-query';
import { css, styled } from 'styled-components';
import { PencilIcon } from 'assets/icons';
import { updateWritingTitle as updateWritingTitleRequest } from 'apis/writings';
import { KeyboardEventHandler, useEffect, useRef } from 'react';
import { getErrorMessage } from 'utils/error';
import { useToast } from 'hooks/@common/useToast';
import { validateWritingTitle } from 'utils/validators';
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
    onSuccess: () => {
      queryClient.invalidateQueries(['writings', writingId]);
      queryClient.invalidateQueries(['writingsInCategory', categoryId]);
    },
    onError: (error) => {
      toast.show({ type: 'error', message: getErrorMessage(error) });
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
      <>
        <S.Input
          type='text'
          placeholder='새 제목을 입력해주세요'
          defaultValue={title}
          value={inputTitle}
          ref={inputRef}
          onChange={(e: any) => setInputTitle(e.target.value)}
          onBlur={resetInput}
          onKeyDown={escapeRename}
          onKeyUp={requestChangedName}
          disabled={!isInputOpen}
        />
        {!isInputOpen && canEditTitle && (
          <S.Button aria-label={'글 제목 수정'} onClick={openInput}>
            <PencilIcon width={20} height={20} />
          </S.Button>
        )}
      </>
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
  `,
};
