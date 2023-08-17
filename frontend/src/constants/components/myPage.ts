import { baseURL, domainURL } from 'constants/apis/url';

export enum ConnectionPlatforms {
  tistory = 'tistory',
  medium = 'medium',
  notion = 'notion',
}

export const getConnectionPlatformRedirectURL = (platform: ConnectionPlatforms) =>
  `${domainURL}/connections/${platform}`;

export const getConnectionPlatformURL = (platform: ConnectionPlatforms) =>
  `${baseURL}/connections/${platform}/redirect?redirect_uri=${getConnectionPlatformRedirectURL(
    platform,
  )}`;
