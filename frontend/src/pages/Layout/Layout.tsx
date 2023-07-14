import { PropsWithChildren } from 'react';
import { styled } from 'styled-components';

const Layout = ({ children }: PropsWithChildren) => {
  return (
    <S.Container>
      <S.Header>서비스 컨트롤바 컴포넌트</S.Header>
      <S.Row>
        <S.SidebarSection>사이드바</S.SidebarSection> {/** 사이드바 컴포넌트 완성되면 대체 */}
        <S.Main>{children}</S.Main>
      </S.Row>
    </S.Container>
  );
};

export default Layout;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    width: 100vw;
    height: 100vh;
    padding: 8px;
  `,

  Header: styled.header`
    flex-shrink: 0;
    height: 4rem;
  `,

  Row: styled.div`
    flex: 1;
    display: flex;
    gap: 0.4rem;
  `,

  SidebarSection: styled.section`
    width: 32rem;
    border: 3px solid black;
    border-radius: 8px;
  `,

  Main: styled.main`
    flex: 1;
  `,
};
