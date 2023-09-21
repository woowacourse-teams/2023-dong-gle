import { useEffect } from 'react';

/**
 * 특정 엘리먼트 '외부'를 클릭했을 때 특정 함수를 실행함
 */
const useOutsideClickEffect = (
  ref: React.RefObject<HTMLElement>,
  onClickOutside: (e: React.MouseEvent) => void,
) => {
  useEffect(() => {
    const handleClickOutside = (e: any) => {
      if (ref.current && !ref.current.contains(e.target)) {
        onClickOutside(e);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [ref, onClickOutside]);
};

export default useOutsideClickEffect;
