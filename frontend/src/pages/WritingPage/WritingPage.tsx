import { PropsWithChildren } from 'react';
import { styled } from 'styled-components';

const WritingPage = ({ children }: PropsWithChildren) => {
  return (
    <S.Container>
      <S.WritingWrapper>{children}</S.WritingWrapper>
      <S.SidebarSection>사이드바</S.SidebarSection> {/** 사이드바 컴포넌트 완성되면 대체 */}
    </S.Container>
  );
};

export default WritingPage;

const S = {
  Container: styled.div`
    display: flex;
    gap: 0.4rem;
    height: 100%;
  `,

  WritingWrapper: styled.article`
    flex: 1;
    border: 3px solid black;
    border-radius: 8px;

    background-color: white;
  `,

  SidebarSection: styled.section`
    width: 32rem;
    border: 3px solid black;
    border-radius: 8px;
  `,
};
