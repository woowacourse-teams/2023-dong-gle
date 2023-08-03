import { useState, useEffect, useCallback, useRef } from 'react';

type Args = {
  onFileChange: (e: React.DragEvent | Event) => void;
};

export const useFileDragAndDrop = ({ onFileChange }: Args) => {
  const [isDragging, setIsDragging] = useState(false);
  const dragRef = useRef<HTMLButtonElement | null>(null);

  const onDragEnter = (e: DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
    setIsDragging(true);
  };

  const onDragLeave = (e: DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
    setIsDragging(false);
  };

  const onDragOver = (e: DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
    if (e.dataTransfer?.files) {
      setIsDragging(true);
    }
  };

  const onDrop = (e: DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
    onFileChange(e);
    setIsDragging(false);
  };

  const initDragEvents = useCallback((): void => {
    if (dragRef.current) {
      dragRef.current.addEventListener('dragenter', onDragEnter);
      dragRef.current.addEventListener('dragleave', onDragLeave);
      dragRef.current.addEventListener('dragover', onDragOver);
      dragRef.current.addEventListener('drop', onDrop);
    }
  }, [onDragEnter, onDragLeave, onDragOver, onDrop]);

  const resetDragEvents = useCallback((): void => {
    if (dragRef.current) {
      dragRef.current.removeEventListener('dragenter', onDragEnter);
      dragRef.current.removeEventListener('dragleave', onDragLeave);
      dragRef.current.removeEventListener('dragover', onDragOver);
      dragRef.current.removeEventListener('drop', onDrop);
    }
  }, [onDragEnter, onDragLeave, onDragOver, onDrop]);

  useEffect(() => {
    initDragEvents();

    return () => resetDragEvents();
  }, [initDragEvents, resetDragEvents]);

  return { dragRef, isDragging };
};
