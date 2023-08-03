import { ChangeEventHandler, KeyboardEventHandler, useEffect, useRef, useState } from 'react';

const useCategoryInput = (initialValue: string) => {
  const [value, setValue] = useState(initialValue);
  const [isInputOpen, setIsInputOpen] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, [isInputOpen]);

  const handleOnChange: ChangeEventHandler<HTMLInputElement> = (e) => setValue(e.target.value);

  const resetInput = () => {
    setIsInputOpen(false);
    setValue('');
  };

  const escapeInput: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key !== 'Escape') return;

    resetInput();
  };

  return {
    value,
    inputRef,
    handleOnChange,
    escapeInput,
    isInputOpen,
    setIsInputOpen,
    resetInput,
  };
};

export default useCategoryInput;
