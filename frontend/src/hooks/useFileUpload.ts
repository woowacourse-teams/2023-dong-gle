import { InputHTMLAttributes, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useMutation from './@common/useMutation';
import { addWriting } from 'apis/writings';
import { AddWritingRequest } from 'types/apis/writings';

export const useFileUpload = (accept: InputHTMLAttributes<HTMLInputElement>['accept'] = '*') => {
  const [selectedFile, setSelectedFile] = useState<FormData | null>(null);
  const { mutateQuery } = useMutation<AddWritingRequest, null>({
    fetcher: (body) => addWriting(body),
    onSuccess: (data) => {
      const writingId = data.headers.get('Location')?.split('/').pop();
      navigate(`/writing/${writingId}`);
    },
  });

  const navigate = useNavigate();

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
