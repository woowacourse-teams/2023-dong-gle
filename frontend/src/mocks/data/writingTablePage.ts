import { GetDetailWritingsResponse } from 'types/apis/writings';

export const writingTable: GetDetailWritingsResponse = {
  id: 2,
  categoryName: '쿠마',
  writings: [
    {
      id: 3,
      title: '곰이란 무엇인가?',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
          publishedUrl: 'https://www.tistory.com/',
        },
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
          publishedUrl: 'https://medium.com/',
        },
      ],
    },
    {
      id: 4,
      title: '쿠마의 일기',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [],
    },
    {
      id: 5,
      title: '곰은 사람을 찢어',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [],
    },
    {
      id: 6,
      title: '겨울잠 잘 자는 법!',
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
          publishedUrl: 'https://medium.com/',
        },
      ],
    },
  ],
};
