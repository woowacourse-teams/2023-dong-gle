export type TistoryConnection = {
  isConnected: boolean;
  blogName: string;
};

export type NotionConnection = {
  isConnected: boolean;
};

export type MediumConnection = {
  isConnected: boolean;
};

export type MemberResponse = {
  id: number;
  name: string;
  tistory: TistoryConnection;
  notion: NotionConnection;
  medium: MediumConnection;
};
