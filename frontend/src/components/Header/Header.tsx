import { SettingIcon, SidebarLeftIcon, SidebarRightIcon } from 'assets/icons';
import { styled } from 'styled-components';
import { HEADER_STYLE } from 'styles/layoutStyle';

type Props = {
  onClickLeftSidebar: () => void;
  onClickRightSidebar: () => void;
  isWritingViewerActive: boolean;
};

const Header = ({ onClickLeftSidebar, onClickRightSidebar, isWritingViewerActive }: Props) => {
  return (
    <S.Container>
      <S.LeftIconsBox>
        <SettingIcon width='2.4rem' height='2.4rem' />
        <button onClick={onClickLeftSidebar}>
          <SidebarLeftIcon width='2.4rem' height='2.4rem' />
        </button>
      </S.LeftIconsBox>
      <S.RightIconsBox>
        {isWritingViewerActive && (
          <button onClick={onClickRightSidebar}>
            <SidebarRightIcon width='2.4rem' height='2.4rem' />
          </button>
        )}
      </S.RightIconsBox>
    </S.Container>
  );
};

export default Header;

const S = {
  Container: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 1rem;
    height: ${HEADER_STYLE.height};
  `,

  LeftIconsBox: styled.div`
    display: flex;
    gap: 0.8rem;
  `,

  RightIconsBox: styled.div``,
};
