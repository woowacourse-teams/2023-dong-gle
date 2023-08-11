import { ChangeEventHandler, KeyboardEventHandler, useEffect, useRef, useState } from 'react';

const useCategoryInput = (initialValue: string) => {
  const [isError, setIsError] = useState(false);
  const [isInputOpen, setIsInputOpen] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, [isInputOpen]);

  const openInput = () => setIsInputOpen(true);

  const resetInput = () => {
    setIsError(false);
    setIsInputOpen(false);
  };

  const escapeInput: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key !== 'Escape') return;

    resetInput();
  };

  return {
    inputRef,
    escapeInput,
    isInputOpen,
    openInput,
    resetInput,
    isError,
    setIsError,
  };
};

export default useCategoryInput;
