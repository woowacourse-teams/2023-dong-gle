import { MediumLogoIcon, TistoryLogoIcon } from 'assets/icons';
import { ReactElement } from 'react';
import { Blog } from 'types/domain';

export const BLOG_LIST = {
  MEDIUM: 'MEDIUM',
  TISTORY: 'TISTORY',
} as const;

export const BLOG_ICON: Record<Blog, ReactElement> = {
  MEDIUM: <MediumLogoIcon width='2.4rem' height='2.4rem' />,
  TISTORY: <TistoryLogoIcon width='2.4rem' height='2.4rem' />,
} as const;
