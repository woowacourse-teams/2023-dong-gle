describe('동글 첫 페이지', () => {
  beforeEach(() => {
    cy.viewport(1440, 810);
    cy.visit(`/`);
  });

  it('카테고리 추가 버튼을 클릭하여 입력 창에 이름을 입력하고 엔터를 쳐서 카테고리를 추가할 수 있다.', () => {
    cy.findByLabelText('카테고리 추가 입력 창 열기').click();
    cy.findByLabelText('카테고리 추가 입력 창').focus().type('동글이{enter}');
    cy.findByText('동글이').should('exist');
  });

  it('카테고리 이름 수정 버튼을 클릭하여 입력 창에 이름을 입력하고 엔터를 쳐서 카테고리 이름을 수정할 수 있다.', () => {
    cy.findByText('동글이').realHover();
    cy.findByLabelText('동글이 카테고리 이름 수정').click();
    cy.findByLabelText('동글이 카테고리 이름 수정 입력 창').focus().type('동글동글이{enter}');
    cy.findByText('동글이').should('not.exist');
    cy.findByText('동글동글이').should('exist');
  });

  it('Add Post 버튼을 누르면 글 가져오기 모달 창이 열린다.', () => {
    cy.findByText('Add Post').click();

    cy.findByText('글 가져오기').should('exist');
  });
});
