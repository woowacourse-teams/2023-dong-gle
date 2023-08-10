import { GetDetailWritingsResponse } from 'types/apis/writings';

export const getWritingTableMock = (categoryId: number): GetDetailWritingsResponse => ({
  id: categoryId,
  categoryName: '우아한테크코스',
  writings: [
    {
      id: 200,
      title: '우테코 레벨 1: 우테코 생활기',
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
      title: '우테코 레벨 2: 레벨 인터뷰 회고',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    // {
    //   id: 35,
    //   title: '우테코 레벨3',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨4',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨5',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨6',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨7',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨8',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨9',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨10',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨11',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨12',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
    // {
    //   id: 36,
    //   title: '우테코 레벨13',
    //   createdAt: new Date('2023-07-11T06:55:46.922Z'),
    //   publishedDetails: [
    //     {
    //       blogName: 'MEDIUM',
    //       publishedAt: new Date('2023-07-11T06:55:46.922Z'),
    //     },
    //     {
    //       blogName: 'TISTORY',
    //       publishedAt: new Date('2023-06-11T06:55:46.922Z'),
    //     },
    //   ],
    // },
  ],
});
