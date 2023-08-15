import { useEffect, useRef, useState } from 'react';
import { DeletedWriting } from 'types/apis/trash';
import { useDeletePermanentWritings } from './useDeletePermanentWritings';
import { useRestoreDeleteWritings } from './useRestoreDeletedWritings';

export const useTrashCanTable = (writings: DeletedWriting[]) => {
  const [writingIds, setWritingIds] = useState<number[]>([]);
  const [isClickedAllCheckbox, setIsClickedAllCheckbox] = useState(false);
  const rowRef = useRef<HTMLTableRowElement>(null);
  const deletePermanentWritingsMutation = useDeletePermanentWritings();
  const restoreDeletedWritingsMutation = useRestoreDeleteWritings();

  useEffect(() => {
    rowRef.current?.focus();
  }, [writings]);

  const toggleAllCheckbox = () => {
    if (isClickedAllCheckbox) {
      // checked 상태 -> 모두 unChecked
      setWritingIds([]);
    } else {
      // unChecked 상태 -> 모두 check
      const allWritingIds = writings.map((writing) => writing.id);
      setWritingIds(allWritingIds);
    }

    setIsClickedAllCheckbox((prev) => !prev);
  };

  const toggleCheckbox = (id: number) => {
    if (writingIds.includes(id)) {
      // checked 상태 -> unCheck
      setWritingIds(writingIds.filter((writingId) => writingId !== id));
    } else {
      // unChecked 상태 -> check
      setWritingIds([...writingIds, id]);
    }
  };

  const getIsChecked = (id: number) => writingIds.includes(id);

  const deletePermanentWritings = () => deletePermanentWritingsMutation(writingIds);

  const restoreDeletedWritings = () => restoreDeletedWritingsMutation(writingIds);

  return {
    rowRef,
    deletePermanentWritings,
    restoreDeletedWritings,
    toggleAllCheckbox,
    toggleCheckbox,
    getIsChecked,
  };
};
