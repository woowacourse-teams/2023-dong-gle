import { HelpMenuBook, HelpMenuFeedback } from 'assets/icons';
import styled from 'styled-components';

type Props = {
  title: string;
  handleMenuItemClick: () => void;
};

const Item = ({ title, handleMenuItemClick }: Props) => {
  return (
    <S.Item>
      {title === '사용법' ? (
        <HelpMenuBook width={16} height={16} />
      ) : (
        <HelpMenuFeedback width={16} height={16} />
      )}
      <button onClick={handleMenuItemClick}>{title}</button>
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
    padding-left: 1rem;
    &:not(:last-child) {
      box-shadow: 0px 1px 0px ${({ theme }) => theme.color.gray4};
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
