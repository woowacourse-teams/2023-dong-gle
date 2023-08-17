import { PostOauthLoginRequest } from 'types/apis/login';
import { http } from './fetch';
import { OauthPlatforms, getOauthURL, getOauthRedirectURL } from 'constants/components/oauth';

export const getRedirection = (platform: OauthPlatforms) =>
  http.get(`${getOauthURL(platform)}?redirect_uri=${getOauthRedirectURL(platform)}`);

export const postOauthLogin = ({
  platform,
  body,
}: PostOauthLoginRequest): Promise<PostOauthLoginResponse> =>
  http.post(getOauthURL(platform), { json: body });
