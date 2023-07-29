import { ChangeEvent, KeyboardEvent, useEffect, useRef, useState } from 'react';
import { usePatchCategory } from './usePatchCategory';

export const useCategoryName = (id: number, categoryName: string) => {
  const [name, setName] = useState(categoryName);
  const [isRenaming, setIsRenaming] = useState(false);
  const [rename, setRename] = useState('');
  const { renameCategory } = usePatchCategory();
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (isRenaming && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isRenaming]);

  const changeName = (e: ChangeEvent<HTMLInputElement>) => setRename(e.target.value);

  const escapeChangeName = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Escape') return;

    setIsRenaming(false);
    setRename('');
  };

  const requestChangedName = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key !== 'Enter') return;

    setIsRenaming(() => false);
    setName(() => rename);
    setRename(() => '');
    renameCategory(id, rename);
  };

  return {
    name,
    isRenaming,
    setIsRenaming,
    rename,
    inputRef,
    changeName,
    escapeChangeName,
    requestChangedName,
  };
};
