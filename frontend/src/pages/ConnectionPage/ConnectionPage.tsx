import { useMutation } from '@tanstack/react-query';
import {
  storeTistoryInfo as storeTistoryInfoRequest,
  // storeMediumInfo as storeMediumInfoRequest,
  storeNotionInfo as storeNotionInfoRequest,
} from 'apis/connections';
import Spinner from 'components/@common/Spinner/Spinner';
import { domainURL } from 'constants/apis/url';
import { ConnectionPlatforms } from 'constants/components/myPage';
import { useEffect } from 'react';
import { useLocation, useSearchParams } from 'react-router-dom';

export const isConnectionPlatforms = (
  platform: string | undefined,
): platform is ConnectionPlatforms => {
  return platform ? platform in ConnectionPlatforms : false;
};

export const useStoreConnectionPlatforms = (platform: string | undefined) => {
  const { mutate: storeTistoryInfo } = useMutation(storeTistoryInfoRequest);
  // const { mutate: storeMediumInfo } = useMutation(storeMediumInfoRequest);
  const { mutate: storeNotionInfo } = useMutation(storeNotionInfoRequest);

  switch (platform) {
    case ConnectionPlatforms.tistory:
      return storeTistoryInfo;
    // case ConnectionPlatforms.medium:
    //   return storeMediumInfo;
    case ConnectionPlatforms.notion:
      return storeNotionInfo;
  }
};

const ConnectionPage = () => {
  const location = useLocation();
  const platform = location.pathname.split('/').pop();
  const mutate = useStoreConnectionPlatforms(platform);
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const code = searchParams.get('code');

    if (!mutate || !code) {
      return;
    }

    mutate({
      code,
      redirect_uri: `${domainURL}/my-page`,
    });
  }, []);

  return <Spinner />;
};

export default ConnectionPage;
