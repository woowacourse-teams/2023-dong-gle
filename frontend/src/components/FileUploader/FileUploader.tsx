import { PlusCircleIcon } from 'assets/icons';
import { useFileInput } from 'hooks/@common/useFileInput';
import { ChangeEvent, PropsWithChildren } from 'react';
import { styled } from 'styled-components';

type Props = {
  acceptableType?: string;
};

const FileUploader = ({ acceptableType = '*' }: PropsWithChildren<Props>) => {
  const { inputRef, openFinder } = useFileInput();

  const uploadOnServer = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    // TODO: 파일 선택 시 서버에 업로드 하는 API 연결
  };

  return (
    <>
      <S.FileInput type='file' accept={acceptableType} ref={inputRef} onChange={uploadOnServer} />
      {/* TODO: 추상화된 버튼 컴포넌트로 교체 예정 */}
      <S.Button onClick={openFinder}>
        <PlusCircleIcon />
        <span>Add Post</span>
      </S.Button>
    </>
  );
};

export default FileUploader;

const S = {
  Button: styled.button`
    display: flex;
    align-items: center;
    gap: 12px;
    font-size: 16px;

    svg {
      width: 30px;
      height: 30px;
    }
  `,
  FileInput: styled.input`
    display: none;
  `,
};
