import { PropsWithChildren } from 'react';

import { DefaultTheme, css, styled } from 'styled-components';
import { theme } from 'styles/theme';

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

export const LAYOUT_COMMON_STYLE = {
  gap: '0.4rem',

  border: (({ theme }: DefaultTheme) => {
    return `2px solid ${theme.color.gray13}`;
  })({ theme }),
} as const;

export const SIDEBAR_SECTION_STYLE = css`
  width: 32rem;
  border: ${LAYOUT_COMMON_STYLE.border};
  border-radius: 8px;
`;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    width: 100vw;
    height: 100vh;
    padding: 0.4rem;
  `,

  Header: styled.header`
    flex-shrink: 0;
    height: 4rem;
  `,

  Row: styled.div`
    flex: 1;
    display: flex;
    gap: ${LAYOUT_COMMON_STYLE.gap};
  `,

  SidebarSection: styled.section`
    ${SIDEBAR_SECTION_STYLE}
  `,

  Main: styled.main`
    flex: 1;
  `,
};
