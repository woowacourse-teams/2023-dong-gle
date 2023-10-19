import { ReactElement } from 'react';
import styled from 'styled-components';

type Props = {
  title: string;
  handleMenuItemClick: () => void;
  icon?: ReactElement;
};

const Item = ({ title, handleMenuItemClick, icon }: Props) => {
  return (
    <S.Item>
      <button onClick={handleMenuItemClick}>
        {title}
        {icon}
      </button>
    </S.Item>
  );
};

export default Item;

const S = {
  Item: styled.li`
    display: flex;
    align-items: center;
    width: 100%;
    height: 4rem;
    padding: 0 0.4rem;
    &:not(:last-child) {
      box-shadow: 0px 1px 0px ${({ theme }) => theme.color.gray4};
    }

    &:hover {
      background-color: ${({ theme }) => theme.color.gray4};
    }

    button {
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 100px;
      height: 100%;
      margin: 0 8px;

      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  `,
};
