import { useMutation } from '@tanstack/react-query';
import { addNotionWriting, addWriting } from 'apis/writings';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { ChangeEventHandler, useState } from 'react';
import { AddWritingRequest } from 'types/apis/writings';

type Args = {
  categoryId: number | null;
  closeModal: () => void;
};

export const useFileUploadModal = ({ categoryId, closeModal }: Args) => {
  const [inputValue, setInputValue] = useState('');
  const { goWritingPage } = usePageNavigate();
  const selectedCategoryId = categoryId ?? 1;

  const { mutate: uploadNotion, isLoading: isNotionUploadLoading } = useMutation(addNotionWriting, {
    onSuccess: (data) => onFileUploadSuccess(data.headers),
  });

  const { mutate: uploadFile, isLoading: isFileUploadLoading } = useMutation(addWriting, {
    onSuccess: (data) => onFileUploadSuccess(data.headers),
  });

  const onFileUploadSuccess = (headers: Headers) => {
    const writingId = headers.get('Location')?.split('/').pop();
    goWritingPage(Number(writingId));
    closeModal();
  };

  const uploadOnServer = (selectedFile: FormData | null) => {
    if (!selectedFile) return;

    selectedFile.append('categoryId', JSON.stringify(selectedCategoryId));

    uploadFile(selectedFile);
  };

  const setNotionPageLink: ChangeEventHandler<HTMLInputElement> = (e) => {
    setInputValue(e.target.value);
  };

  const uploadNotionWriting = () => {
    const blockId = inputValue.split('/')?.pop()?.split('?')?.shift()?.split('-').pop();

    if (!blockId) return;

    setInputValue('');

    uploadNotion({
      blockId: blockId,
      categoryId: selectedCategoryId,
    });
  };

  const isLoading = isNotionUploadLoading || isFileUploadLoading;

  return { isLoading, inputValue, uploadOnServer, setNotionPageLink, uploadNotionWriting };
};
