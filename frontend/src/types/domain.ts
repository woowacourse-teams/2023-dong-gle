import { BLOG_LIST } from 'constants/blog';

export type Blog = (typeof BLOG_LIST)[keyof typeof BLOG_LIST];

export type PublishingPropertyData = {
  tags: string[];
};
