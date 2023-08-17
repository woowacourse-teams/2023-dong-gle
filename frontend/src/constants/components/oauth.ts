import { baseURL, domainURL } from 'constants/apis/url';

export enum OauthPlatforms {
  kakao = 'kakao',
}

export const getOauthRedirectPlatformURL = (platform: OauthPlatforms) =>
  `${domainURL}/oauth/login/${platform}`;

export const getOauthPlatformURL = (platform: OauthPlatforms) =>
  `${baseURL}/oauth/login/${platform}/redirect?redirect_uri=${getOauthRedirectPlatformURL(
    platform,
  )}`;
