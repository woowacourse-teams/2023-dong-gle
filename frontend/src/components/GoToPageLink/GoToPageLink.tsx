import { PropsWithChildren } from 'react';
import { Link, useLocation } from 'react-router-dom';
import styled from 'styled-components';

type Props = {
  path: string;
} & PropsWithChildren;

const GoToPageLink = ({ path, children = null }: Props) => {
  const location = useLocation();

  return (
    <S.Link to={path} $isClicked={location.pathname === path}>
      {children}
    </S.Link>
  );
};

export default GoToPageLink;

const S = {
  Link: styled(Link)<{ $isClicked: boolean }>`
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

    &:visited {
      color: ${({ theme }) => theme.color.gray13};
      text-decoration: none;
    }
  `,
};
