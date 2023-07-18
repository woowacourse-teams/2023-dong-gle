import { BLOG } from 'constants/blog';

export type AddWritingRequest = FormData;

export type GetWritingResponse = {
  id: number;
  title: string;
  content: string;
};

export type GetWritingPropertiesResponse = {
  createdAt: Date;
  isPublished: boolean;
  publishedAt: Date;
  publishedTo: string[]; // TODO: Blog string literal 타입으로 변경
};

export type PublishToArg = (typeof BLOG)[keyof typeof BLOG];

export type PublishWritingRequest = {
  publishTo: PublishToArg; // TODO: Blog string literal 타입으로 변경
};

export type PublishWritingArgs = {
  writingId: number;
  body: PublishWritingRequest;
};
