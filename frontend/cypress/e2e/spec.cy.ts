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

  it('카테고리 삭제 버튼을 클릭하여 카테고리를 삭제할 수 있다.', () => {
    cy.findByText('동글동글이').realHover();
    cy.findByLabelText('동글동글이 카테고리 삭제').click();
    cy.findByText('동글동글이').should('not.exist');
  });

  it('Add Post 버튼을 누르면 글 가져오기 모달 창이 열린다.', () => {
    cy.findByText('Add Post').click();

    cy.findByText('글 가져오기').should('exist');
  });

  it('드래그 앤 드롭으로 마크다운 파일을 업로드할 수 있다.', () => {
    cy.findByText('Add Post').click();
    cy.findByLabelText('파일 업로드 존').attachFile('markdown-test.md', {
      subjectType: 'drag-n-drop',
    });

    cy.findByText('markdown-test').should('exist');
    cy.findByText('e2e 테스트를 위한 마크다운 파일입니다.').should('exist');
    cy.findByText('글 정보').should('exist');
    cy.findByLabelText('오른쪽 사이드바 토글').should('exist');
  });

  it('기본 카테고리에서 업로드한 글을 확인할 수 있다.', () => {
    cy.findByText('기본').click();

    cy.findAllByText('markdown-test').should('exist');
  });
});
