import { OauthPlatform } from 'types/apis/login';

export const baseURL = process.env.BASE_URL;
export const writingURL = `${baseURL}/writings`;
export const categoryURL = `${baseURL}/categories`;

export const redirectLocalURL = (platform: OauthPlatform) =>
  `http://localhost:3000/oauth/${platform}`; // 데브 서버용
const redirectURL = (platform: OauthPlatform) => `https://donggle.blog/oauth/${platform}`; // 프로덕션 서버용

export const oauthRedirectURL = (platform: OauthPlatform) =>
  `${baseURL}/oauth/login/${platform}?redirect_uri=${redirectLocalURL(platform)}`;

export const oauthLoginURL = (platform: OauthPlatform) => `${baseURL}/oauth/login/${platform}`;
