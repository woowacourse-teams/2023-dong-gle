import { fireEvent } from '@storybook/testing-library';
import { MOCK_ACCESS_TOKEN } from '../../src/mocks/auth';

describe('스페이스 페이지 왼쪽 사이드바', () => {
  beforeEach(() => {
    cy.viewport(1440, 810);
    cy.window().its('localStorage').invoke('setItem', 'accessToken', MOCK_ACCESS_TOKEN);

    cy.visit('/space').wait(1000);
  });

  describe('글 가져오기 테스트', () => {
    it('글 가져오기 버튼을 누르면 글 가져오기 모달 창이 열린다.', () => {
      cy.findByText('글 가져오기').click();

      cy.findByText('내 컴퓨터에서 가져오기').should('exist');
    });

    it('드래그 앤 드롭으로 마크다운 파일을 업로드할 수 있다.', () => {
      cy.findByText('글 가져오기').click();
      cy.findByLabelText('파일 업로드').attachFile('markdown-test.md', {
        subjectType: 'drag-n-drop',
      });

      cy.findByText('글 정보').should('exist');
      cy.findByLabelText('오른쪽 사이드바 토글').should('exist');
      cy.findByLabelText('글 제목 수정').should('exist');
    });
  });

  describe('카테고리 테스트', () => {
    it('카테고리를 추가할 수 있다.', () => {
      cy.findByLabelText('카테고리 추가 입력 창 열기').click().wait(1000);
      cy.findByLabelText('카테고리 추가 입력 창').focus().type('동글이{enter}').wait(1000);
      cy.findByText('동글이').should('exist');
    });

    it('카테고리 이름을 수정할 수 있다.', () => {
      cy.findByLabelText('쿠마 카테고리 메인 화면에 열기').realHover().wait(1000);

      cy.findByLabelText('쿠마 카테고리 이름 수정').click().wait(1000);
      cy.findByLabelText('쿠마 카테고리 이름 수정 입력 창').focus().type('짜잔{enter}').wait(1000);

      cy.findByLabelText('쿠마 카테고리 메인 화면에 열기').should('not.exist');
      cy.findByLabelText('쿠마짜잔 카테고리 메인 화면에 열기').should('exist');
    });

    it('카테고리를 삭제할 수 있다.', () => {
      cy.findByLabelText('쿠마 카테고리 메인 화면에 열기').realHover().wait(1000);

      cy.findByLabelText('쿠마 카테고리 삭제').click().wait(1000);

      cy.findByLabelText('쿠마 카테고리 메인 화면에 열기').should('not.exist');
    });

    it('글을 삭제하면 휴지통 페이지로 이동한다.', () => {
      cy.findByLabelText('쿠마 카테고리 왼쪽 사이드바에서 열기').click().wait(1000);
      cy.findByLabelText('곰이란 무엇인가?글 메인화면에 열기').realHover().wait(1000);

      cy.findByLabelText('곰이란 무엇인가?글 삭제').click();

      cy.location('pathname').should('eq', '/space/trash-can');
    });
  });
});
