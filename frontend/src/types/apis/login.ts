import { OauthPlatforms } from 'constants/components/oauth';

export type PostOauthLoginRequest = {
  platform: OauthPlatforms;
  body: {
    code: string;
    redirect_uri: string;
  };
};
