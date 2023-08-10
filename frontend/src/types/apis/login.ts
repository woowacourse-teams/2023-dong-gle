export type OauthPlatform = 'kakao';

export type PostOauthLogin = {
  code: string;
  redirect_uri: string;
};
