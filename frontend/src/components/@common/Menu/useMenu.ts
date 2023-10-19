import useOutsideClickEffect from 'hooks/@common/useOutsideClickEffect';
import { useRef, useState } from 'react';

export const useMenu = (initialIsOpen: boolean) => {
  const [isOpen, setIsOpen] = useState(initialIsOpen);
  const menuRef = useRef<HTMLDivElement>(null);

  const closeMenu = () => setIsOpen(false);

  useOutsideClickEffect(menuRef, closeMenu);

  const toggleIsOpen = () => setIsOpen(!isOpen);

  return { menuRef, isOpen, toggleIsOpen };
};
