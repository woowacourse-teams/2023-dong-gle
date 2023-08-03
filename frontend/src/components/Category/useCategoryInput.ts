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

  const resetValue = () => setValue('');

  const closeInput = () => {
    setIsInputOpen(false);
    resetValue();
  };

  const escapeInput: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key !== 'Escape') return;

    closeInput();
  };

  return {
    value,
    resetValue,
    inputRef,
    handleOnChange,
    escapeInput,
    isInputOpen,
    setIsInputOpen,
    closeInput,
  };
};

export default useCategoryInput;
