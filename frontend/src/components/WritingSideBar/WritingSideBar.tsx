import PublishingPropertySection from 'components/PublishingPropertySection/PublishingPropertySection';
import PublishingSection from 'components/PublishingSection/PublishingSection';
import { styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';
import { useCurrentTab } from './useCurrentTab';
import { InfoIcon, PublishingIcon } from 'assets/icons';
import { useState } from 'react';
import { Blog } from 'types/domain';
import WritingPropertySection from 'components/WritingPropertySection/WritingPropertySection';

export enum TabKeys {
  WritingProperty = 'WritingProperty',
  Publishing = 'Publishing',
  PublishingProperty = 'PublishingProperty',
}

type Props = { writingId: number };

const WritingSideBar = ({ writingId }: Props) => {
  const { currentTab, changeCurrentTab } = useCurrentTab<TabKeys>(TabKeys.WritingProperty);
  const [publishTo, setPublishTo] = useState<Blog | null>(null);

  const changePublishTo = (blog: Blog) => {
    setPublishTo(blog);
  };

  const menus = [
    {
      key: TabKeys.WritingProperty,
      label: <InfoIcon width={24} height={24} />,
      content: <WritingPropertySection writingId={writingId} />,
    },
    {
      key: TabKeys.Publishing,
      label: <PublishingIcon width={24} height={24} />,
      content: (
        <PublishingSection onTabClick={changeCurrentTab} onBlogButtonClick={changePublishTo} />
      ),
    },
    {
      key: TabKeys.PublishingProperty,
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
    <S.SidebarContainer>
      <S.MenuTabList>
        {menus
          .filter((menu) => menu.key !== TabKeys.PublishingProperty)
          .map((menu) => (
            <S.Tab key={menu.key}>
              <S.Input
                type='radio'
                name='tab'
                id={menu.key}
                checked={currentTab === menu.key}
                onChange={() => changeCurrentTab(menu.key)}
              />
              <S.Label htmlFor={menu.key}>{menu.label}</S.Label>
            </S.Tab>
          ))}
      </S.MenuTabList>
      {menus.find((menu) => menu.key === currentTab)?.content}
    </S.SidebarContainer>
  );
};

export default WritingSideBar;

const S = {
  SidebarContainer: styled.div`
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
