type ConnectionInfo = {
  code: string;
  redirect_uri: string;
};

type Token = {
  token: string;
};

export type TistoryInfo = ConnectionInfo;
export type NotionInfo = ConnectionInfo;
export type MediumInfo = Token;
