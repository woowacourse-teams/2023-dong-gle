describe('메인 페이지', () => {
  beforeEach(() => {
    cy.viewport(1440, 810);
    cy.visit(`/`);
  });

  describe('로그인 성공 테스트', () => {
    it('로그인 하기 버튼을 누르면 로그인 모달 창이 열린다.', () => {
      cy.findByText('로그인하기').click();

      cy.findByText('간편 로그인').should('exist');
    });

    it('카카오 로그인 버튼을 누르면 카카오로 로그인 할 수 있는 화면이 나타난다.', () => {
      cy.findByText('로그인하기').click();
      cy.findByLabelText('카카오 로그인 화면으로 이동').click();
      cy.visit(`/oauth/login/kakao?code=mock`);
      cy.findByText('로그아웃').should('exist');
    });
  });

  describe('로그인 실패 테스트', () => {
    it('카카오 로그인 버튼을 누르면 카카오로 로그인 할 수 있는 화면이 나타난다.', () => {
      cy.findByText('로그인하기').click();
      cy.findByLabelText('카카오 로그인 화면으로 이동').click();
      cy.visit(`/oauth/login/kakao`);
      cy.findByText('로그인을 실패했습니다').should('exist');
    });
  });
});
