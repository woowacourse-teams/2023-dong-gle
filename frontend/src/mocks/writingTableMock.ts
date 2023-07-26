import { GetCategoryIdWritingListResponse } from 'types/apis/writings';

export const getWritingTableMock = (categoryId: number): GetCategoryIdWritingListResponse => ({
  id: categoryId,
  categoryName: '우아한테크코스',
  writings: [
    {
      id: 33,
      title: '우테코 레벨1',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-06-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 34,
      title: '우테코 레벨2',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-06-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 35,
      title: '우테코 레벨3',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-06-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 36,
      title: '우테코 레벨4',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-06-11T06:55:46.922Z'),
        },
      ],
    },
  ],
});
