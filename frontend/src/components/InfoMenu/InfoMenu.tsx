import Menu from 'components/@common/Menu/Menu';
import styled from 'styled-components';
import { useInfoMenu } from './useInfoMenu';
import { Meatballs } from 'assets/icons';

const InfoMenu = () => {
  const infos = useInfoMenu();

  return (
    <S.InfoMenu>
      <Meatballs width={20} height={20} />
      <Menu verticalDirection='down' horizonDirection='left'>
        {infos.map(({ icon, title, handleMenuItemClick }) => {
          return (
            <Menu.Item
              key={title}
              icon={icon}
              title={title}
              handleMenuItemClick={handleMenuItemClick}
            />
          );
        })}
      </Menu>
    </S.InfoMenu>
  );
};

export default InfoMenu;

const S = {
  InfoMenu: styled.button`
    position: relative;
    border-radius: 4px;

    padding: 0.8rem;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray4};
    }
  `,
};
