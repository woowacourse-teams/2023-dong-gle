import { MOCK_ACCESS_TOKEN } from '../../src/mocks/auth';

describe('마이 페이지', () => {
  beforeEach(() => {
    cy.viewport(1440, 810);
    cy.window().its('localStorage').invoke('setItem', 'accessToken', MOCK_ACCESS_TOKEN);
    cy.visit(`/my-page`);
  });

  describe('회원 탈퇴 테스트', () => {
    it('회원 탈퇴한다.', () => {
      cy.window()
        .its('localStorage')
        .invoke('getItem', 'accessToken')
        .should('eq', MOCK_ACCESS_TOKEN);

      cy.findByText('탈퇴하기').click();
      cy.findByText('탈퇴').click();
      cy.wait(1000);

      cy.window().its('localStorage').invoke('getItem', 'accessToken').should('eq', null);

      cy.location('pathname').should('eq', '/');
    });
  });
});
