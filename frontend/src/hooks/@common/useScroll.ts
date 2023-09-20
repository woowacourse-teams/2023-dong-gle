import { DragEventHandler, useRef } from 'react';

export const useScroll = () => {
  const scrollRef = useRef<HTMLElement | null>(null);

  const scrollToBottom = () => {
    if (scrollRef.current) {
      const lastItem = scrollRef.current.lastChild;
      if (lastItem && lastItem instanceof HTMLElement) {
        lastItem.scrollIntoView({ behavior: 'smooth', block: 'end' });
      }
    }
  };

  const scrollInArea =
    (margin: number, speed: number): DragEventHandler =>
    (e) => {
      const areaRect = scrollRef.current?.getBoundingClientRect();

      if (!areaRect) return;

      let scrollY: number;

      if (e.clientY < areaRect.top + margin) {
        scrollY = Math.floor(Math.max(-1, (e.clientY - areaRect.top) / margin - 1) * speed);
      } else if (e.clientY > areaRect.bottom - margin) {
        scrollY = Math.ceil(Math.min(1, (e.clientY - areaRect.bottom) / margin + 1) * speed);
      } else {
        scrollY = 0;
      }

      if (scrollY !== 0 && scrollRef.current) {
        scrollRef.current.scrollBy({ top: scrollY, behavior: 'smooth' });
      }
    };

  return { scrollRef, scrollToBottom, scrollInArea };
};
