import { oauthLoginURL, oauthRedirectURL } from 'constants/apis/url';
import { http } from './fetch';
import { OauthPlatform, PostOauthLoginRequest } from 'types/apis/login';

export const getRedirection = (platform: OauthPlatform) => http.get(oauthRedirectURL(platform));

export const postOauthLogin = ({ platform, body }: PostOauthLoginRequest) =>
  http.post(oauthLoginURL(platform), { json: body });
