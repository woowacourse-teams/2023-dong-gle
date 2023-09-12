import { baseURL, domainURL } from 'constants/apis/url';

export enum OauthPlatforms {
  kakao = 'kakao',
}

export const getOauthRedirectPlatformURL = (platform: OauthPlatforms) =>
  `${domainURL}/oauth/login/${platform}`;

// 변경 전 url: ${baseURL}/oauth/login/${platform}/redirect?redirect_uri=${getOauthRedirectPlatformURL(platform)}
// 변경 후 url: ${baseURL}/oauth/login/${platform}?redirect_uri=${getOauthRedirectPlatformURL(platform)}
// ${baseURL}/oauth/login/${platform} <- '/redirect' 붙고 안 붙고 차이
export const getOauthPlatformURL = (platform: OauthPlatforms) =>
  `${baseURL}/auth/login/${platform}/redirect?redirect_uri=${getOauthRedirectPlatformURL(
    platform,
  )}`;
