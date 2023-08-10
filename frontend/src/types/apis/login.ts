export type OauthPlatform = 'kakao';

export type PostOauthLogin = {
  platform: OauthPlatform;
  body: {
    code: string;
    redirect_uri: string;
  };
};
