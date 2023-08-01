import { useState } from 'react';

export const useCurrentTab = <TabKey>(defaultTabKey: TabKey) => {
  const [currentTab, setCurrentTab] = useState(defaultTabKey);

  const changeCurrentTab = (tabKey: TabKey) => {
    setCurrentTab(tabKey);
  };

  return { currentTab, changeCurrentTab };
};
