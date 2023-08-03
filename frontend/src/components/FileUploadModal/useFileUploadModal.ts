import { addNotionWriting, addWriting } from 'apis/writings';
import useMutation from 'hooks/@common/useMutation';
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
  const addedCategoryId = categoryId ?? 1;

  const { mutateQuery: uploadNotion, isLoading: isNotionUploadLoading } = useMutation({
    fetcher: addNotionWriting,
    onSuccess: (data) => onFileUploadSuccess(data.headers),
  });
  const { mutateQuery: uploadFile, isLoading: isFileUploadLoading } = useMutation<
    AddWritingRequest,
    null
  >({
    fetcher: addWriting,
    onSuccess: (data) => onFileUploadSuccess(data.headers),
  });

  const onFileUploadSuccess = (headers: Headers) => {
    const writingId = headers.get('Location')?.split('/').pop();
    goWritingPage(Number(writingId));
    closeModal();
  };

  const uploadOnServer = async (selectedFile: FormData | null) => {
    if (!selectedFile) return;

    selectedFile.append('categoryId', JSON.stringify(addedCategoryId));

    await uploadFile(selectedFile);
  };

  const setNotionPageLink: ChangeEventHandler<HTMLInputElement> = (e) => {
    setInputValue(e.target.value);
  };

  const uploadNotionWriting = async () => {
    const blockId = inputValue.split('/')?.pop()?.split('?')?.shift()?.split('-').pop();

    if (!blockId) return;

    setInputValue('');

    await uploadNotion({
      blockId: blockId,
      categoryId: addedCategoryId,
    });
  };

  const isLoading = isNotionUploadLoading || isFileUploadLoading;

  return { isLoading, inputValue, uploadOnServer, setNotionPageLink, uploadNotionWriting };
};
