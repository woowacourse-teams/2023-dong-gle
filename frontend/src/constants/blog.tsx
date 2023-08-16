import { ReactElement } from 'react';
import { MediumLogoIcon, TistoryLogoIcon } from 'assets/icons';
import type { Blog } from 'types/domain';

export const BLOG_LIST = {
  MEDIUM: 'MEDIUM',
  TISTORY: 'TISTORY',
} as const;

export const BLOG_KOREAN = {
  MEDIUM: '미디엄',
  TISTORY: '티스토리',
} as const;

export const BLOG_ICON: Record<Blog, ReactElement> = {
  MEDIUM: <MediumLogoIcon />,
  TISTORY: <TistoryLogoIcon />,
} as const;
