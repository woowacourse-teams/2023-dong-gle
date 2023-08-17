import { baseURL } from 'constants/apis/url';

export enum OauthPlatforms {
  kakao = 'kakao',
}

const domainURL = process.env.DOMAIN_URL;
export const getOauthURL = (platform: OauthPlatforms) => `${baseURL}/oauth/login/${platform}`;
export const getRedirectURL = (platform: OauthPlatforms) => `${domainURL}/oauth/login/${platform}`;
