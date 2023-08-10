import { OauthPlatform } from 'types/apis/login';

export const baseURL = process.env.BASE_URL;
export const writingURL = `${baseURL}/writings`;
export const categoryURL = `${baseURL}/categories`;

const REDIRECT_URI_DEV = 'http://localhost:3000/oauth'; // 데브 서버용
const REDIRECT_URI = 'https://donggle.blog/oauth'; // 프로덕션 서버용

export const oauthRedirectURL = (platform: OauthPlatform) =>
  `${baseURL}/oauth/login/${platform}?redirect_uri=${REDIRECT_URI_DEV}`;

export const oauthLoginURL = (platform: OauthPlatform) => `${baseURL}/oauth/login/${platform}`;
