import { Blog } from 'types/domain';
import { CategoryResponse } from './category';

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

export type PublishWritingToTistoryRequest = {
  tags: string[];
  publishStatus: 'PUBLIC' | 'PRIVATE' | 'PROTECT';
  password: string;
  categoryId: string;
  publishTime: string; // "yyyy-MM-dd HH:mm:ss.SSS" 형식
};

export type PublishWritingToTistoryArgs = {
  writingId: number;
  body: PublishWritingToTistoryRequest;
};

export type PublishWritingToMediumRequest = {
  tags: string[];
  publishStatus: 'PUBLIC' | 'PRIVATE' | 'PROTECT';
};

export type PublishWritingToMediumArgs = {
  writingId: number;
  body: PublishWritingToMediumRequest;
};

export type PublishedDetail = {
  blogName: Blog;
  publishedAt: Date;
  tags: string[];
  publishedUrl: string | null;
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

export type UpdateWritingTitleArgs = {
  writingId: number;
  body: Pick<Writing, 'title'>;
};

export type UpdateWritingOrderArgs = {
  writingId: number;
  body: {
    targetCategoryId: number;
    nextWritingId: number;
  };
};

type HomeContent = {
  id: number;
  title: string;
  category: CategoryResponse;
  createdAt: Date;
  publishedDetails: Omit<PublishedDetail, 'tags' | 'publishedUrl'>[];
};

type Sort = {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
};

type Pageable = {
  sort: Sort;
  offset: number;
  pageNumber: number;
  pageSize: number;
  paged: boolean;
  unpaged: boolean;
};

export type GetHomeWritingsResponse = {
  content: HomeContent[];
  pageable: Pageable;
  totalPages: number;
  totalElements: number;
  last: boolean;
  size: number;
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
};
