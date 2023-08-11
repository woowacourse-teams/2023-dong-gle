import { OauthPlatform } from 'types/apis/login';

export const Platforms = {
  kakao: 'kakao',
} as const;

const domainURL = process.env.DOMAIN_URL;
export const getRedirectURL = (platform: OauthPlatform) => `${domainURL}/oauth/login/${platform}`;
