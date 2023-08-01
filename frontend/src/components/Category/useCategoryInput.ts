import { ChangeEvent, KeyboardEvent, useEffect, useRef, useState } from 'react';

const useCategoryInput = (initialValue: string) => {
  const [value, setValue] = useState(initialValue);
  const [isOpenInput, setIsOpenInput] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, [isOpenInput]);

  const handleOnChange = (e: ChangeEvent<HTMLInputElement>) => setValue(e.target.value);

  const resetValue = () => setValue('');

  const escapeInput = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Escape') return;

    setIsOpenInput(false);
    resetValue();
  };

  return {
    value,
    resetValue,
    inputRef,
    handleOnChange,
    escapeInput,
    isOpenInput,
    setIsOpenInput,
  };
};

export default useCategoryInput;
