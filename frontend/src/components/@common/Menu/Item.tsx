import styled from 'styled-components';

type Props = {
  title: string;
  handleMenuItemClick: () => void;
};

const Item = ({ title, handleMenuItemClick }: Props) => {
  return (
    <S.Item>
      <button onClick={handleMenuItemClick}>{title}</button>
    </S.Item>
  );
};

export default Item;

const S = {
  Item: styled.li`
    display: flex;
    align-items: center;
    width: 20rem;
    height: 4rem;

    &:not(:last-child) {
      border-bottom: 2px solid ${({ theme }) => theme.color.gray4};
    }

    &:hover {
      background-color: ${({ theme }) => theme.color.gray4};
    }

    button {
      width: 100%;
      height: 100%;
      margin: 0 8px;

      text-align: start;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  `,
};
