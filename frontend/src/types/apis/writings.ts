import { Blog } from 'types/domain';

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
  publishedTo: Blog;
};

export type PublishWritingRequest = {
  publishTo: Blog;
};

export type PublishWritingArgs = {
  writingId: number;
  body: PublishWritingRequest;
};

export type PublishedDetail = {
  blogName: string;
  publishedAt: Date;
};

export type Writing = {
  id: number;
  title: string;
  createdAt: Date;
  publishedDetails: PublishedDetail[];
};

export type GetCategoryIdWritingListResponse = {
  id: number;
  categoryName: string;
  writings: Writing[];
};
