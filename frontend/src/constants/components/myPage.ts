import { baseURL, domainURL } from 'constants/apis/url';

export enum ConnectionPlatforms {
  tistory = 'tistory',
  medium = 'medium',
  notion = 'notion',
}

export const getConnectionPlatformRedirectURL = (platform: ConnectionPlatforms) =>
  `${domainURL}/my-page/connections/${platform}`;
export const getConnectionPlatformURL = (platform: ConnectionPlatforms) =>
  `${baseURL}/connections/${platform}?redirect_uri=${getConnectionPlatformRedirectURL(platform)}`;
