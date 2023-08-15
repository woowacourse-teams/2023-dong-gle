export const PATH = {
  app: '',
  introducePage: '',
  oauthPage: 'oauth/login/*',
  kakao: 'kakao',
  space: 'space',
  writingPage: 'writings/:categoryId/:writingId',
  writingTablePage: 'writings/:categoryId',
  trashCanPage: 'trash-can',
} as const;

export const NAVIGATE_PATH = {
  homePage: `/${PATH.space}`,
  getWritingTablePage: (categoryId: number) => `/${PATH.space}/writings/${categoryId}`,
  getWritingPage: (categoryId: number, writingId: number) =>
    `/${PATH.space}/writings/${categoryId}/${writingId}`,
  trashCanPage: `/${PATH.space}/${PATH.trashCanPage}`,
} as const;
