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

  describe('연결 테스트', () => {
    it('티스토리와 연결한다.', () => {
      cy.findByLabelText('티스토리 연결하기').click().wait(1000);

      cy.visit(`/connections/tistory?code=mock`);

      cy.findByLabelText('티스토리 연결 해제하기').should('exist');
    });

    it('미디엄과 연결한다.', () => {
      cy.findByLabelText('미디엄 연결하기').click();

      cy.findByLabelText('미디엄 토큰 입력 창').focus().type('mediumToken{enter}');

      cy.findByLabelText('미디엄 연결 해제하기').should('exist');
    });

    it('노션과 연결한다.', () => {
      cy.findAllByLabelText('노션 연결하기').click().wait(1000);

      cy.visit(`/connections/notion?code=mock`);

      cy.findByLabelText('노션 연결 해제하기').should('exist');
    });
  });
});
