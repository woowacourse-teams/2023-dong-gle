import { InputHTMLAttributes, useEffect } from 'react';
import { css, styled } from 'styled-components';
import { useFileUpload } from 'hooks/useFileUpload';
import { useFileDragAndDrop } from 'hooks/@common/useFileDragAndDrop';
import { ImportIcon } from 'assets/icons';

type Props = {
  accept?: InputHTMLAttributes<HTMLInputElement>['accept'];
  width?: string;
  height?: string;
  onFileSelect: (file: FormData | null) => void;
};

const FileUploader = ({ accept = '*', width = '30rem', height = '10rem', onFileSelect }: Props) => {
  const { onFileChange, openFinder, selectedFile } = useFileUpload(accept);
  const { dragRef, isDragging } = useFileDragAndDrop({ onFileChange });

  useEffect(() => {
    onFileSelect(selectedFile);
  }, [selectedFile]);

  return (
    <button ref={dragRef} onClick={openFinder}>
      <S.Description
        $isDragging={isDragging}
        $width={width}
        $height={height}
        aria-label='파일 업로드'
      >
        <ImportIcon />
        드래그하거나 클릭해서 업로드
      </S.Description>
    </button>
  );
};

export default FileUploader;

const S = {
  Description: styled.div<{ $isDragging: boolean; $width: string; $height: string }>`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    ${({ $width, $height }) => {
      return css`
        width: ${$width};
        height: ${$height};
      `;
    }};
    border: 2px dashed ${({ theme }) => theme.color.gray6};
    background-color: ${({ theme }) => theme.color.gray4};
    font-size: 1.3rem;
    color: ${({ theme }) => theme.color.gray7};
    transition: all 0.2s ease-in-out;

    ${({ $isDragging, theme }) => {
      return (
        $isDragging &&
        css`
          border: 2px dashed ${theme.color.primary};
          background-color: ${theme.color.gray5};
        `
      );
    }}
    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
  SpinnerWrapper: styled.div<{ $width: string; $height: string }>`
    ${({ $width, $height }) => {
      return css`
        width: ${$width};
        height: ${$height};
      `;
    }};

    display: flex;
    justify-content: center;
  `,
};
