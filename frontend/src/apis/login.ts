import { authURL, loginURL } from 'constants/apis/url';
import { http } from './fetch';
import {
  LoginOauthRequest,
  LoginOauthResponse,
  RegenerateAccessTokenResponse,
} from 'types/apis/login';

export const loginOauth = ({ platform, body }: LoginOauthRequest): Promise<LoginOauthResponse> =>
  http.post(`${loginURL}/${platform}`, { json: body });

export const logout = () => http.post(`${authURL}/logout`);

export const regenerateAccessToken = (): Promise<RegenerateAccessTokenResponse> =>
  http.post(`${authURL}/token/refresh`);
