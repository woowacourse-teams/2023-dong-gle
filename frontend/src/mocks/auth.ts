import { DefaultBodyType, PathParams, RestRequest } from 'msw';

export const ACCESS_TOKEN = 'mockAccessToken';

export const ERROR_RESPONSE = {
  error: {
    message: '유효하지 않은 토큰입니다.',
    hint: 'AccessToken이 만료되었습니다. RefreshToken값을 요청하세요.',
    code: 4011,
  },
};

export const isValidAccessToken = (req: RestRequest<DefaultBodyType, PathParams<string>>) => {
  const authorizationHeader = req.headers.get('Authorization');

  if (authorizationHeader === `Bearer ${ACCESS_TOKEN}`) return true;
  return false;
};
