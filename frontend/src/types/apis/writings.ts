import { Blog, PublishingPropertyData } from 'types/domain';

export type AddWritingRequest = FormData;

export type AddNotionWritingRequest = {
  blockId: string;
  categoryId: number;
};

export type GetWritingResponse = {
  id: number;
  title: string;
  content: string;
};

export type GetWritingPropertiesResponse = {
  createdAt: Date;
  publishedDetails: PublishedDetail[];
};

export type PublishWritingRequest = {
  publishTo: Blog;
} & PublishingPropertyData;

export type PublishWritingArgs = {
  writingId: number;
  body: PublishWritingRequest;
};

export type PublishedDetail = {
  blogName: Blog;
  publishedAt: Date;
  tags: string[];
};

export type Writing = {
  id: number;
  title: string;
  createdAt: Date;
  publishedDetails: Omit<PublishedDetail, 'tags'>[];
};

export type GetDetailWritingsResponse = {
  id: number;
  categoryName: string;
  writings: Writing[];
};
