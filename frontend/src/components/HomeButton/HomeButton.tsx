import { HomeBorderIcon } from 'assets/icons';
import { NAVIGATE_PATH } from 'constants/path';
import { usePageNavigate } from 'hooks/usePageNavigate';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';

const HomeButton = () => {
  const location = useLocation();
  const { goSpacePage } = usePageNavigate();

  return (
    <S.Button onClick={goSpacePage} $isClicked={location.pathname === NAVIGATE_PATH.spacePage}>
      <HomeBorderIcon />
      <S.Title>전체 글</S.Title>
    </S.Button>
  );
};

export default HomeButton;

const S = {
  Button: styled.button<{ $isClicked: boolean }>`
    display: flex;
    align-items: center;
    gap: 8px;
    width: 100%;
    height: 3.6rem;
    padding: 8px;
    background-color: ${({ theme, $isClicked }) => $isClicked && theme.color.gray4};
    border-radius: 4px;

    text-align: start;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray3};
    }
  `,

  Title: styled.p`
    font-size: 1.4rem;
    font-weight: 500;
  `,
};
