import { addWriting } from 'apis/writings';
import { InputHTMLAttributes, useEffect, useState } from 'react';
import { AddWritingRequest } from 'types/apis/writings';
import useMutation from './@common/useMutation';

export const useFileUpload = (accept: InputHTMLAttributes<HTMLInputElement>['accept'] = '*') => {
  const [selectedFile, setSelectedFile] = useState<FormData | null>(null);
  const { mutateQuery } = useMutation<AddWritingRequest, null>({
    fetcher: (body) => addWriting(body),
  });

  const onFileChange = (e: Event) => {
    const target = e.target as HTMLInputElement;
    if (!target.files) return;

    const newFile = target.files[0];

    const formData = new FormData();
    formData.append('file', newFile);

    setSelectedFile(formData);
  };

  const uploadOnServer = async (selectedFile: FormData | null) => {
    if (!selectedFile) return;

    await mutateQuery(selectedFile);
  };

  const openFinder = () => {
    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.onchange = onFileChange;
    fileInput.accept = accept;
    fileInput.click();
  };

  useEffect(() => {
    uploadOnServer(selectedFile);
  }, [selectedFile]);

  return { openFinder };
};
