import { useRef } from 'react';

export const useFileInput = () => {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const openFinder = () => {
    if (!inputRef.current) return;
    inputRef.current.click();
  };

  return { inputRef, openFinder };
};
