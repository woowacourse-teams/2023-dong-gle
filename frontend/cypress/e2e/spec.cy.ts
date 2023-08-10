describe('동글 첫 페이지', () => {
  beforeEach(() => {
    cy.viewport(1440, 810);
    cy.visit(`/`);
  });

  it('Add Post 버튼을 누르면 글 가져오기 모달 창이 열린다.', () => {
    cy.findByText('Add Post').click();

    cy.findByText('글 가져오기').should('exist');
  });
});
