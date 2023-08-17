import { baseURL, domainURL } from 'constants/apis/url';

export enum ConnectionPlatforms {
  tistory = 'tistory',
  medium = 'medium',
  notion = 'notion',
}

export const getConnectionPlatformRedirectURL = (platform: ConnectionPlatforms) =>
  `${domainURL}/connections/${platform}`;

// 변경 전 url: ${baseURL}/connections/${platform}/redirect?redirect_uri=${getConnectionPlatformRedirectURL(platform)}
// 변경 후 url: ${baseURL}/connections/${platform}?redirect_uri=${getConnectionPlatformRedirectURL(platform)}
// ${baseURL}/connections/${platform} <- '/redirect' 붙고 안 붙고 차이
export const getConnectionPlatformURL = (platform: ConnectionPlatforms) =>
  `${baseURL}/connections/${platform}/redirect?redirect_uri=${getConnectionPlatformRedirectURL(
    platform,
  )}`;
