import DOMPurify from 'dompurify';
import hljs from 'highlight.js';
import { KeyboardEventHandler, useEffect, useRef, useState } from 'react';
import { styled } from 'styled-components';
import { getWriting, updateWritingTitle as updateWritingTitleRequest } from 'apis/writings';
import Divider from 'components/@common/Divider/Divider';
import Spinner from 'components/@common/Spinner/Spinner';
import { PencilIcon } from 'assets/icons';
import useCategoryInput from 'components/Category/useCategoryInput';
import Input from 'components/@common/Input/Input';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

type Props = {
  writingId: number;
  categoryId: number;
};

const WritingViewer = ({ writingId, categoryId }: Props) => {
  const myRef = useRef<HTMLHeadingElement>(null);
  const {
    inputRef,
    escapeInput: escapeRename,
    isInputOpen,
    openInput,
    resetInput,
    isError,
  } = useCategoryInput('');
  const queryClient = useQueryClient();
  const { data, isLoading } = useQuery(['writings', writingId], () => getWriting(writingId));
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

  useEffect(() => {
    hljs.highlightAll();
  }, [data]);

  if (isLoading) {
    return (
      <S.LoadingContainer>
        <Spinner size={60} thickness={4} />
        <h1>글을 불러오는 중입니다 ...</h1>
      </S.LoadingContainer>
    );
  }

  return (
    <S.WritingViewerContainer>
      <S.TitleWrapper>
        {isInputOpen ? (
          <Input
            type='text'
            variant='unstyled'
            size='large'
            placeholder='새 제목을 입력해주세요'
            ref={inputRef}
            isError={isError}
            onBlur={resetInput}
            onKeyDown={escapeRename}
            onKeyUp={requestChangedName}
          />
        ) : (
          <>
            <S.Title ref={myRef} tabIndex={0}>
              {data?.title}
            </S.Title>
            <S.Button aria-label={'글 제목 수정'} onClick={openInput}>
              <PencilIcon width={20} height={20} />
            </S.Button>
          </>
        )}
      </S.TitleWrapper>
      <Divider />
      <S.ContentWrapper
        tabIndex={0}
        dangerouslySetInnerHTML={{
          __html: DOMPurify.sanitize(data?.content ?? '글 내용이 없습니다'),
        }}
      />
    </S.WritingViewerContainer>
  );
};

export default WritingViewer;

const S = {
  WritingViewerContainer: styled.section`
    padding: 8rem 4rem;
    max-width: 100%;
    overflow-wrap: break-word;
  `,

  LoadingContainer: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 2rem;
    max-width: 100%;
    height: 100%;
  `,

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

  ContentWrapper: styled.section`
    padding: 1.6rem 0;
    font-size: 1.6rem;

    h1 {
      margin: 3.4rem 0 1.7rem;
      font-size: 3.4rem;
    }

    h2 {
      margin: 2.8rem 0 1.4rem;
      font-size: 2.8rem;
    }

    h3 {
      margin: 2.2rem 0 1.1rem;
      font-size: 2.2rem;
    }

    h4 {
      margin: 1.6rem 0 0.8rem;
      font-size: 1.6rem;
    }

    h5 {
      margin: 1.3rem 0 0.65rem;
      font-size: 1.3rem;
    }

    h6 {
      margin: 1rem 0 0.5rem;
      font-size: 1rem;
    }

    p {
      margin: 1.6rem 0;
      font-size: 1.6rem;
    }

    blockquote {
      padding: 1rem 2rem;
      margin: 1.6rem 0;
      border-left: 4px solid ${({ theme }) => theme.color.gray8};
      background-color: ${({ theme }) => theme.color.gray2};
      color: ${({ theme }) => theme.color.gray9};
      line-height: 2.4rem;
    }

    ol,
    ul {
      padding-left: 2rem;
      margin: 1rem 0;
    }

    ul > li {
      list-style: disc;
    }

    ol > li {
      list-style: decimal;
    }

    li {
      margin-bottom: 10px;
    }

    a {
      color: #0968da;
      text-decoration: underline;
      &:visited {
        color: #0968da;
      }
    }

    code {
      padding: 0.2rem 0.4rem;
      margin: 0.1rem;
      border: none solid #eee;
      border-radius: 4px;
      background-color: ${({ theme }) => theme.color.gray4};
      color: #d71919;
    }

    pre > code {
      color: inherit;
    }

    img {
      max-width: 100%;
      height: auto;
    }

    strong {
      font-weight: bold;
    }

    em {
      font-style: italic;
    }
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
};
