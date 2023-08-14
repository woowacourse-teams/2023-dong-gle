import { getOauthURL } from 'constants/apis/url';
import { http } from './fetch';
import { OauthPlatform, PostOauthLoginRequest } from 'types/apis/login';
import { getRedirectURL } from 'constants/apis/oauth';

export const getRedirection = (platform: OauthPlatform) =>
  http.get(`${getOauthURL(platform)}?redirect_uri=${getRedirectURL(platform)}`);

export const postOauthLogin = ({ platform, body }: PostOauthLoginRequest) =>
  http.post(getOauthURL(platform), { json: body });
