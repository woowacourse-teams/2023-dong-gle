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

export type PublishWritingRequest = {
  publishTo: string; // TODO: Blog string literal 타입으로 변경
};
