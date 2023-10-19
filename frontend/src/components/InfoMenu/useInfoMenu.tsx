import { HelpMenuBook, HelpMenuFeedback, LogoutIcon } from 'assets/icons';
import { useLogout } from 'hooks/@common/useLogout';

export const useInfoMenu = () => {
  const logout = useLogout();

  const infos = [
    {
      icon: <HelpMenuBook width={16} height={16} />,
      title: '사용법',
      handleMenuItemClick: () =>
        window.open(
          'https://github.com/woowacourse-teams/2023-dong-gle/wiki/%EB%8F%99%EA%B8%80-%EB%8F%84%EC%9B%80%EB%A7%90',
        ),
    },
    {
      icon: <HelpMenuFeedback width={16} height={16} />,
      title: '피드백',
      handleMenuItemClick: () => window.open('https://forms.gle/wSjCQKb4jhmFwSWQ9'),
    },
    {
      icon: <LogoutIcon width={16} height={16} />,
      title: '로그아웃',
      handleMenuItemClick: () => logout.mutate(),
    },
  ];

  return infos;
};
