import { SettingIcon, SidebarLeftIcon, SidebarRightIcon } from 'assets/icons';
import { styled } from 'styled-components';

type Props = {};

const Header = ({}: Props) => {
  return (
    <S.Container>
      <div className='left'>
        <SettingIcon width='2.4rem' height='2.4rem' className='icon' />
        <SidebarLeftIcon width='2.4rem' height='2.4rem' className='icon' />
      </div>
      <div className='right'>
        <SidebarRightIcon width='2.4rem' height='2.4rem' className='icon' />
      </div>
    </S.Container>
  );
};

export default Header;

const S = {
  Container: styled.div`
    display: flex;
    justify-content: space-between;
    margin: 0 1rem;
    height: 3rem;

    .left {
      display: flex;
      gap: 0.8rem;
    }

    .icon {
      cursor: pointer;
    }
  `,
};
