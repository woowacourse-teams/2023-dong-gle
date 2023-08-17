import { OauthPlatform } from 'types/apis/login';

export const baseURL = process.env.BASE_URL;
export const domainURL = process.env.DOMAIN_URL;

export const writingURL = `${baseURL}/writings`;
export const categoryURL = `${baseURL}/categories`;
export const trashURL = `${baseURL}/trash`;
export const getOauthURL = (platform: OauthPlatform) => `${baseURL}/oauth/login/${platform}`;
export const memberURL = `${baseURL}/member`;
