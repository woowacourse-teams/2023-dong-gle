import { useState } from 'react';

export const useCurrentTab = (defaultTabKey: number) => {
  const [currentTab, setCurrentTab] = useState(defaultTabKey);

  const changeCurrentTab = (tabKey: number) => {
    setCurrentTab(tabKey);
  };

  return { currentTab, changeCurrentTab };
};
