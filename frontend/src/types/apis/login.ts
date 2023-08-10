import { Platforms } from 'constants/apis/oauth';

export type OauthPlatform = (typeof Platforms)[keyof typeof Platforms];

export type PostOauthLogin = {
  platform: OauthPlatform;
  body: {
    code: string;
    redirect_uri: string;
  };
};
