import { GetWritingPropertiesResponse } from 'types/apis/writings';

const writingContentMock = `
<h1>동글이란?</h1><h2>블로그 글 관리의 새로운 해결책</h2><p><code>노션</code> 같은 <strong><em>텍스트 에디터</strong></em> 에서 작성한 글을 블로그에 올린 적이 있으신가요?</p><p>직접 글을 블로그로 올리다보면 그 과정이 귀찮아지고 어떤 글을 옮겼는지 헷갈릴 때가 많습니다.</p><p>이 문제를 해결하기 위해 우리는 <a href=\"https://donggle.blog/\"><strong>동글</strong></a> 서비스를 만들었습니다.</p><h3>주요 기능</h3><ol><li><strong>노션 글 업로드</strong>: 노션에 작성한 글을 간편하게 동글에 업로드할 수 있습니다. (마크다운 파일 업로드도 지원)</li><li><strong>카테고리 분류</strong>: 동글은 업로드한 글을 카테고리로 분류하여 모아볼 수 있는 기능을 제공합니다.</li><li><strong>다양한 블로그 플랫폼 지원</strong>: 작성한 글을 <code>Tistory</code>나 <code>Medium</code>와 같은 블로그 플랫폼에 발행할 수 있습니다.</li><li><strong>발행 정보 투명화</strong>: 글의 작성 일자와 발행된 블로그 정보를 통해 글을 효율적으로 관리할 수 있습니다.</li></ol><p>다양한 곳에서 글을 작성 후 쉽게 블로그로 포스팅하고 싶은 분들은 <strong>동글</strong>의 도움을 받아보세요.</p><p>더 많은 시간과 에너지를 여러분의 글 작성과 이야기에 투자할 수 있을 것입니다.</p><blockquote><a href=\"https://www.donggle.blog/\"><strong>동글</strong></a>과 함께라면 블로그 글 관리는 더 이상 고민거리가 아닙니다.</blockquote><p>이제 글 작성에 더 집중하며, 블로그 관리에 소비되는 시간과 에너지를 절약하세요!</p>`;

export const writing = {
  id: 1,
  title: '동글을 소개합니다 🎉',
  content: writingContentMock,
};

export const writingProperties: GetWritingPropertiesResponse = {
  createdAt: new Date('2023-07-11T06:55:46.922Z'),
  publishedDetails: [
    {
      blogName: 'MEDIUM',
      publishedAt: new Date('2023-07-11T06:55:46.922Z'),
      tags: ['개발', '네트워크', '서버'],
      publishedUrl: 'https://medium.com/',
    },
    {
      blogName: 'TISTORY',
      publishedAt: new Date('2023-06-11T06:55:46.922Z'),
      tags: ['프로그래밍', 'CS'],
      publishedUrl: 'https://www.tistory.com/',
    },
  ],
};
