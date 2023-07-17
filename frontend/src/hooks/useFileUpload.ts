import { InputHTMLAttributes, useEffect, useState } from 'react';

export const useFileUpload = (accept: InputHTMLAttributes<HTMLInputElement>['accept'] = '*') => {
  const [selectedFile, setSelectedFile] = useState<FormData | null>(null);

  const onFileChange = (e: Event) => {
    const target = e.target as HTMLInputElement;
    if (!target.files) return;

    const newFile = target.files[0];
    const fileType = newFile.type;

    if (!fileType.includes('md')) {
      // TODO: 파일 타입 검증
      return;
    }

    const formData = new FormData();
    formData.append('file', newFile);

    setSelectedFile(formData);
  };

  const uploadOnServer = (selectedFile: FormData | null) => {
    if (!selectedFile) return;

    // TODO: 파일 선택 시 서버에 업로드 하는 API 연결
    console.log('upload', selectedFile);
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
