import { InputHTMLAttributes, useState } from 'react';

export const useFileUpload = (accept: InputHTMLAttributes<HTMLInputElement>['accept'] = '*') => {
  const [selectedFile, setSelectedFile] = useState<FormData | null>(null);

  const onFileChange = (e: React.DragEvent | Event) => {
    const target = 'dataTransfer' in e ? e.dataTransfer : (e.target as HTMLInputElement);

    if (!target.files) return;

    const newFile = target.files[0];

    if (newFile.size > 5 * 1024 * 1024) {
      alert('업로드 가능한 최대 용량은 5MB입니다. ');
      return;
    }

    const formData = new FormData();
    formData.append('file', newFile);

    setSelectedFile(formData);
  };

  const openFinder = () => {
    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.onchange = onFileChange;
    fileInput.accept = accept;
    fileInput.click();
  };

  return { selectedFile, openFinder, onFileChange };
};
