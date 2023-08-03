import PublishingPropertySection from 'components/PublishingPropertySection/PublishingPropertySection';
import PublishingSection from 'components/PublishingSection/PublishingSection';
import { css, styled } from 'styled-components';
import { sidebarStyle } from 'styles/layoutStyle';
import { useCurrentTab } from './useCurrentTab';
import { InfoIcon, PublishingIcon } from 'assets/icons';
import { useState } from 'react';
import { Blog } from 'types/domain';
import WritingPropertySection from 'components/WritingPropertySection/WritingPropertySection';
import Button from 'components/@common/Button/Button';

export enum TabKeys {
  WritingProperty = 'WritingProperty',
  Publishing = 'Publishing',
  PublishingProperty = 'PublishingProperty',
}

type Props = { writingId: number };

const ariaLabelFromTabKeys = {
  [TabKeys.WritingProperty]: '글 정보',
  [TabKeys.Publishing]: '발행 하기',
  [TabKeys.PublishingProperty]: '발행 정보',
};

const WritingSideBar = ({ writingId }: Props) => {
  const { currentTab, selectCurrentTab } = useCurrentTab<TabKeys>(TabKeys.WritingProperty);
  const [publishTo, setPublishTo] = useState<Blog | null>(null);

  const selectPublishTo = (blog: Blog) => {
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
        <PublishingSection onTabClick={selectCurrentTab} onBlogButtonClick={selectPublishTo} />
      ),
    },
    {
      key: TabKeys.PublishingProperty,
      label: 'PublishingProperty',
      content: publishTo && (
        <PublishingPropertySection
          writingId={writingId}
          publishTo={publishTo}
          selectCurrentTab={selectCurrentTab}
        />
      ),
    },
  ];

  return (
    <S.SidebarContainer>
      <S.MenuTabList role='tablist'>
        {menus
          .filter((menu) => menu.key !== TabKeys.PublishingProperty)
          .map((menu) => (
            <S.Tab key={menu.key}>
              <S.Button
                $checked={currentTab === menu.key}
                onClick={() => selectCurrentTab(menu.key)}
                aria-label={ariaLabelFromTabKeys[menu.key]}
              >
                {menu.label}
              </S.Button>
            </S.Tab>
          ))}
      </S.MenuTabList>
      <div role='tabpanel' aria-labelledby={currentTab}>
        {menus.find((menu) => menu.key === currentTab)?.content}
      </div>
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
  Label: styled.label`
    input[type='radio']:checked + & {
      transition: all 0.2s ease-in;
      background-color: ${({ theme }) => theme.color.gray1};
    }
  `,
  Button: styled.button<{ $checked: boolean }>`
    display: flex;

    justify-content: center;
    align-items: center;
    padding: 0.8rem;
    width: 100%;
    border-radius: 8px;

    ${({ $checked }) =>
      $checked
        ? css`
            transition: all 0.2s ease-in;
            background-color: ${({ theme }) => theme.color.gray1};
          `
        : css`
            &:hover {
              background-color: ${({ theme }) => theme.color.gray6};
            }
          `}
  `,
};
