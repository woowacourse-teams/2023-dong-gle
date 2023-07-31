import PublishingPropertySection from 'components/PublishingPropertySection/PublishingPropertySection';
import PublishingSection from 'components/PublishingSection/PublishingSection';
import { styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';
import { useCurrentTab } from './useCurrentTab';
import { InfoIcon, PublishingIcon } from 'assets/icons';
import { useState } from 'react';
import { Blog } from 'types/domain';

export const TAB_KEYS = {
  WritingProperty: 1,
  Publishing: 2,
  PublishingProperty: 3,
};

type Props = { writingId: number };

const WritingSideBar = ({ writingId }: Props) => {
  const { currentTab, changeCurrentTab } = useCurrentTab(TAB_KEYS.WritingProperty);
  const [publishTo, setPublishTo] = useState<Blog | null>(null);

  const changePublishTo = (blog: Blog) => {
    setPublishTo(blog);
  };

  const menus = [
    {
      key: TAB_KEYS.WritingProperty,
      name: 'WritingProperty',
      label: <InfoIcon width={24} height={24} />,
      content: <></>, // TODO: 글 정보 탭 구현
    },
    {
      key: TAB_KEYS.Publishing,
      name: 'Publishing',
      label: <PublishingIcon width={24} height={24} />,
      content: (
        <PublishingSection changeCurrentTab={changeCurrentTab} changePublishTo={changePublishTo} />
      ),
    },
    {
      key: TAB_KEYS.PublishingProperty,
      name: 'PublishingProperty',
      label: 'PublishingProperty',
      content: publishTo && (
        <PublishingPropertySection
          writingId={writingId}
          publishTo={publishTo}
          changeCurrentTab={changeCurrentTab}
        />
      ),
    },
  ];

  return (
    <S.SidebarSection>
      <S.MenuTabList>
        {menus
          .filter((menu) => menu.key !== TAB_KEYS.PublishingProperty)
          .map((menu) => (
            <S.Tab key={menu.key}>
              <S.Input
                type='radio'
                name='tab'
                id={menu.name}
                checked={currentTab === menu.key}
                onClick={() => changeCurrentTab(menu.key)}
              />
              <S.Label htmlFor={menu.name}>{menu.label}</S.Label>
            </S.Tab>
          ))}
      </S.MenuTabList>
      {menus.find((menu) => menu.key === currentTab)?.content}
    </S.SidebarSection>
  );
};

export default WritingSideBar;

const S = {
  SidebarSection: styled.section`
    ${sidebarStyle}

    display: flex;
    flex-direction: column;
    gap: 2rem;
    overflow: hidden;
  `,
  MenuTabList: styled.ul`
    display: flex;
    gap: 0.4rem;
    padding: 0.5rem;
    border-radius: 8px;
    background-color: ${({ theme }) => theme.color.gray5};
  `,
  Tab: styled.li`
    display: flex;
    width: 100%;
  `,
  Input: styled.input`
    appearance: none;
  `,
  Label: styled.label`
    display: flex;

    justify-content: center;
    align-items: center;
    padding: 0.8rem;
    width: 100%;
    border-radius: 8px;

    cursor: pointer;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray6};
    }

    input[type='radio']:checked + & {
      transition: all 0.2s ease-in;
      background-color: ${({ theme }) => theme.color.gray1};
    }
  `,
};
