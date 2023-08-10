import { oauthLoginURL, oauthRedirectURL } from 'constants/apis/url';
import { http } from './fetch';
import { OauthPlatform, PostOauthLogin } from 'types/apis/login';

export const getRedirection = (platform: OauthPlatform) => http.get(oauthRedirectURL(platform));

export const postOauthLogin = ({ platform, body }: PostOauthLogin) =>
  http.post(oauthLoginURL(platform), {
    headers: {
      'Content-Type': 'application/json',
    },
    json: body,
  });
