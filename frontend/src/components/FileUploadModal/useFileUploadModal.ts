import { useMutation, QueryClient, useQueryClient } from '@tanstack/react-query';
import { addNotionWriting, addWriting } from 'apis/writings';
import { useToast } from 'hooks/@common/useToast';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { ChangeEventHandler, useState } from 'react';
import { getErrorMessage } from 'utils/error';

type Args = {
  categoryId: number;
  closeModal: () => void;
};

export const useFileUploadModal = ({ categoryId, closeModal }: Args) => {
  const [inputValue, setInputValue] = useState('');
  const toast = useToast();
  const { goWritingPage } = usePageNavigate();
  const queryClient = useQueryClient();

  const { mutate: uploadNotion, isLoading: isNotionUploadLoading } = useMutation(addNotionWriting, {
    onSuccess: (data) => {
      onFileUploadSuccess(data.headers);
    },
    onError: (error) => {
      toast.show({ type: 'error', message: getErrorMessage(error) });
    },
  });

  const { mutate: uploadFile, isLoading: isFileUploadLoading } = useMutation(addWriting, {
    onSuccess: (data) => onFileUploadSuccess(data.headers),
    onError: (error) => {
      toast.show({ type: 'error', message: getErrorMessage(error) });
    },
  });

  const onFileUploadSuccess = (headers: Headers) => {
    const writingId = headers.get('Location')?.split('/').pop();
    queryClient.invalidateQueries(['writingsInCategory', categoryId]);
    goWritingPage({ categoryId, writingId: Number(writingId), isDeletedWriting: false });
    closeModal();
  };

  const uploadOnServer = (selectedFile: FormData | null) => {
    if (!selectedFile) return;

    selectedFile.append('categoryId', JSON.stringify(categoryId));

    uploadFile(selectedFile);
  };

  const setNotionPageLink: ChangeEventHandler<HTMLInputElement> = (e) => {
    setInputValue(e.target.value);
  };

  const uploadNotionWriting = () => {
    try {
      const blockId = inputValue.split('/')?.pop()?.split('?')?.shift()?.split('-').pop();

      if (!blockId) {
        throw new Error('노션 페이지 링크를 입력해주세요.');
      }

      setInputValue('');

      uploadNotion({
        blockId: blockId,
        categoryId,
      });
    } catch (error) {
      alert(getErrorMessage(error));
    }
  };

  const isLoading = isNotionUploadLoading || isFileUploadLoading;

  return { isLoading, inputValue, uploadOnServer, setNotionPageLink, uploadNotionWriting };
};
