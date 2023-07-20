import { PropsWithChildren } from 'react';
import { DefaultTheme, css, styled } from 'styled-components';
import { PlusCircleIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { useFileUpload } from 'hooks/useFileUpload';

import { theme } from 'styles/theme';

const Layout = ({ children }: PropsWithChildren) => {
  const { openFinder } = useFileUpload('.md');

  return (
    <S.Container>
      <S.Header />
      <S.Row>
        <S.SidebarSection>
          <Button
            size={'large'}
            icon={<PlusCircleIcon />}
            block={true}
            align='left'
            onClick={openFinder}
          >
            Add Post
          </Button>
        </S.SidebarSection>
        {/** 사이드바 컴포넌트 완성되면 대체 */}
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
  padding: 2rem;
  flex: 0 0 32rem;
`;

const S = {
  Container: styled.div`
    display: flex;
    flex-direction: column;
    width: 100vw;
    height: 100vh;
    padding: 0 1rem;
  `,

  Header: styled.header`
    flex-shrink: 0;
    height: 1rem;
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
