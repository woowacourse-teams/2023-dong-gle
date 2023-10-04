import { GetHomeWritingsResponse } from 'types/apis/writings';

export const homepageWritingTable: GetHomeWritingsResponse = {
  content: [
    {
      id: 1,
      title: '동글을 소개합니다 🎉',
      category: {
        id: 1,
        categoryName: '기본',
      },
      createdAt: new Date('2023-06-01T06:55:46.922Z'),
      publishedDetails: [],
    },
    {
      id: 2,
      title: '백엔드 친구들을 소개합니다(에코, 토리, 비버, 헙크)',
      category: {
        id: 1,
        categoryName: '기본',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 3,
      title: '곰이란 무엇인가?',
      category: {
        id: 2,
        categoryName: '쿠마',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 4,
      title: '쿠마의 일기',
      category: {
        id: 2,
        categoryName: '쿠마',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [],
    },
    {
      id: 5,
      title: '곰은 사람을 찢어',
      category: {
        id: 2,
        categoryName: '쿠마',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [],
    },
    {
      id: 6,
      title: '겨울잠 잘 자는 법!',
      category: {
        id: 2,
        categoryName: '쿠마',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 7,
      title: '아이스 아메리카노가 좋은 이유',
      category: {
        id: 3,
        categoryName: '아커',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 8,
      title: '거북선과 도너츠 잘 만드는 법',
      category: {
        id: 3,
        categoryName: '아커',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 9,
      title: '소주와 맥주가 1000원 밖에 안하는 용호낙지 솔직 후기',
      category: {
        id: 3,
        categoryName: '아커',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'MEDIUM',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 10,
      title: `I'm fine thank you! and you?`,
      category: {
        id: 4,
        categoryName: '파인',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [],
    },
    {
      id: 11,
      title: '메이플 아르테일 레벨 별 추천 사냥터 모음 (1 ~ 100)',
      category: {
        id: 4,
        categoryName: '파인',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [
        {
          blogName: 'TISTORY',
          publishedAt: new Date('2023-07-11T06:55:46.922Z'),
        },
      ],
    },
    {
      id: 12,
      title: '나는 칼바람 나락이 좋다',
      category: {
        id: 4,
        categoryName: '파인',
      },
      createdAt: new Date('2023-07-11T06:55:46.922Z'),
      publishedDetails: [],
    },
  ],
  pageable: {
    sort: {
      empty: true,
      sorted: false,
      unsorted: true,
    },
    offset: 0,
    pageNumber: 0,
    pageSize: 20,
    paged: true,
    unpaged: false,
  },
  totalPages: 20,
  totalElements: 20,
  last: true,
  size: 20,
  number: 0,
  sort: {
    empty: true,
    sorted: false,
    unsorted: true,
  },
  numberOfElements: 20,
  first: true,
  empty: false,
};
