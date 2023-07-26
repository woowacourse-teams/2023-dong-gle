import { PropsWithChildren } from 'react';
import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';
import { PlusCircleIcon } from 'assets/icons';
import Button from 'components/@common/Button/Button';
import { useFileUpload } from 'hooks/useFileUpload';

import { LAYOUT_STYLE, sidebarStyle } from 'styles/layoutStyle';

const Layout = () => {
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
        <S.Main>
          <Outlet />
        </S.Main>
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
    padding: ${LAYOUT_STYLE.padding};
  `,

  Header: styled.header`
    flex-shrink: 0;
    height: 1rem;
  `,

  Row: styled.div`
    flex: 1;
    display: flex;
    gap: ${LAYOUT_STYLE.gap};
  `,

  SidebarSection: styled.section`
    ${sidebarStyle}
  `,

  Main: styled.main`
    flex: 1;
  `,
};
