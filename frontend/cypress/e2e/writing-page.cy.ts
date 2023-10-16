import { MOCK_ACCESS_TOKEN } from '../../src/mocks/auth';

describe('글 페이지', () => {
  beforeEach(() => {
    cy.viewport(1440, 810);
    cy.window().its('localStorage').invoke('setItem', 'accessToken', MOCK_ACCESS_TOKEN);

    cy.visit('/space').wait(1000);
    cy.findByText('동글을 소개합니다 🎉').click().wait(1000);
  });

  describe('헤더 테스트', () => {
    it('글 페이지에서 오른쪽 사이드바 토글이 잘 된다.', () => {
      cy.findByText('글 정보').should('be.visible');

      cy.findByLabelText('오른쪽 사이드바 토글').click();
      cy.findByText('글 정보').should('not.be.visible');

      cy.findByLabelText('오른쪽 사이드바 토글').click();
      cy.findByText('글 정보').should('be.visible');
    });
  });

  describe('글 테스트', () => {
    it('글 제목을 변경한다.', () => {
      cy.findByLabelText('기본 카테고리 왼쪽 사이드바에서 열기').click().wait(1000);

      cy.findByLabelText('글 제목 수정').click();
      cy.findByPlaceholderText('새 제목을 입력해주세요').focus().type('짜잔{enter}').wait(1000);

      cy.findAllByText('동글을 소개합니다 🎉짜잔').should('exist');
      cy.findAllByText('동글을 소개합니다 🎉').should('not.exist');
    });

    it('발행 하기 탭이 있다.', () => {
      cy.findByLabelText('발행 하기').click();
      cy.findByText('발행하기').should('be.visible');
    });
  });

  describe('휴지통 글 테스트', () => {
    beforeEach(() => {
      cy.findByText('휴지통').click().wait(1000);
      cy.findByText('너 버려진거야').click().wait(1000);
    });

    it('글 제목을 변경할 수 없다.', () => {
      cy.findByLabelText('글 제목 수정').should('not.exist');
    });

    it('발행 하기 탭이 없다.', () => {
      cy.findByLabelText('발행 하기').should('not.exist');
    });
  });
});
