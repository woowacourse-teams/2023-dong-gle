import { ChangeEventHandler, KeyboardEventHandler, useState } from 'react';

export const useTagInput = () => {
  const [tags, setTags] = useState<string[]>([]);
  const [inputValue, setInputValue] = useState('');

  /**
   * enter나 ',' 키를 이용하여 태그를 추가한다.
   * 이 때 정규식에 벗어나면 필터링하고 추가한다.
   * 중복되는 태그, 빈 태그은 추가하지 않는다.
   * */
  const addTag: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (!['Enter', ','].includes(e.key) || !inputValue.length) return;

    const filtered = inputValue.replace(/[^0-9a-zA-Zㄱ-힣.!? ]/g, '');

    if (filtered.length && !tags.includes(filtered)) {
      setTags((prev) => [...prev, filtered]);
    }
    setInputValue('');
  };

  // Backspace 키를 이용하여 마지막 태그를 제거한다.
  const removeLastTag: KeyboardEventHandler<HTMLInputElement> = (e) => {
    if (e.key === 'Backspace' && !inputValue.length && tags.length) {
      const tagsCopy = [...tags];
      const poppedTag = tagsCopy.pop();

      if (typeof poppedTag === 'string') {
        setTags(tagsCopy);
        setInputValue(poppedTag);
      }
    }
  };

  const removeTag = (tag: string) => () => {
    setTags((prevState) => prevState.filter((prevTag) => prevTag !== tag));
  };

  const onInputValueChange: ChangeEventHandler<HTMLInputElement> = (e) => {
    setInputValue(e.target.value);
  };

  return {
    tags,
    inputValue,
    addTag,
    removeLastTag,
    removeTag,
    onInputValueChange,
  };
};
