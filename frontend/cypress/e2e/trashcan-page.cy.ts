import { MOCK_ACCESS_TOKEN } from '../../src/mocks/auth';

describe('휴지통 페이지', () => {
  beforeEach(() => {
    cy.viewport(1440, 810);
    cy.window().its('localStorage').invoke('setItem', 'accessToken', MOCK_ACCESS_TOKEN);

    cy.visit('/space/trash-can').wait(1000);
  });

  describe('글 삭제 테스트', () => {
    it('왼쪽 카테고리에서 글 삭제 시 삭제한 글을 휴지통 테이블에서 볼 수 있다.', () => {
      cy.findByLabelText('쿠마 카테고리 왼쪽 사이드바에서 열기').click().wait(1000);
      cy.findByLabelText('곰이란 무엇인가?글 메인화면에 열기').realHover().wait(1000);
      cy.findByLabelText('곰이란 무엇인가?글 삭제').click();

      cy.findByText('나도 버려졌어 3').should('be.visible');
    });

    it('글을 영구삭제 한다.', () => {
      cy.findByLabelText('휴지통 글 너 버려진거야 선택').click();
      cy.findByText('영구 삭제').click().wait(1000);

      cy.findByText('너 버려진거야').should('not.exist');
    });

    it('글 삭제 시 "글이 삭제되었습니다." toast가 보인다.', () => {
      cy.findByLabelText('휴지통 글 너 버려진거야 선택').click();
      cy.findByText('영구 삭제').click().wait(1000);

      cy.findByText('글이 삭제되었습니다.').should('be.visible');
    });

    it('휴지통에 글이 없으면 "휴지통이 비어있어요"가 보인다.', () => {
      cy.findByLabelText('휴지통 글 너 버려진거야 선택').click();
      cy.findByLabelText('휴지통 글 너 버려진거야2 선택').click();
      cy.findByText('영구 삭제').click().wait(1000);

      cy.findByText('휴지통이 비어있어요.').should('be.visible');
    });
  });
});
