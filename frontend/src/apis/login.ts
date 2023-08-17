import { PostOauthLoginRequest } from 'types/apis/login';
import { http } from './fetch';
import { OauthPlatforms, getOauthURL, getRedirectURL } from 'constants/components/oauth';

export const getRedirection = (platform: OauthPlatforms) =>
  http.get(`${getOauthURL(platform)}?redirect_uri=${getRedirectURL(platform)}`);

export const postOauthLogin = ({ platform, body }: PostOauthLoginRequest) =>
  http.post(getOauthURL(platform), { json: body });
