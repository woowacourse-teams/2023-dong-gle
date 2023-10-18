import { useSetGlobalState } from '@yogjin/react-global-state';
import { mediaQueryMobileState } from 'globalState';
import { useCallback, useEffect, useRef } from 'react';

const MOBILE_MEDIA_QUERY_SIZE = '(max-width: 820px)';

export const useMediaQuery = () => {
  const setIsMobile = useSetGlobalState(mediaQueryMobileState);
  const mediaQueryRef = useRef<MediaQueryList | null>(null);

  const handleWindowResize = useCallback(() => {
    setIsMobile(window.matchMedia(MOBILE_MEDIA_QUERY_SIZE).matches);
  }, [setIsMobile]);

  useEffect(() => {
    setIsMobile(window.matchMedia(MOBILE_MEDIA_QUERY_SIZE).matches);
  }, [setIsMobile]);

  useEffect(() => {
    const mediaQueryList = window.matchMedia(MOBILE_MEDIA_QUERY_SIZE);
    mediaQueryRef.current = mediaQueryList;

    mediaQueryRef.current.addEventListener('change', handleWindowResize);

    return () => {
      mediaQueryRef.current?.removeEventListener('change', handleWindowResize);
    };
  }, [handleWindowResize]);
};
