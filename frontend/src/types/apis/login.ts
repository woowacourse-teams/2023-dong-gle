import { OauthPlatforms } from 'constants/components/oauth';

export type LoginOauthRequest = {
  platform: OauthPlatforms;
  body: {
    code: string;
    redirect_uri: string;
  };
};

export type LoginOauthResponse = {
  accessToken: string;
};
