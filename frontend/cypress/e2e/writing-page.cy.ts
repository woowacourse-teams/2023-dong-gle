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
      cy.findByText('글 정보').should('not.visible');

      cy.findByLabelText('오른쪽 사이드바 토글').click();
      cy.findByText('글 정보').should('be.visible');
    });
  });
});
