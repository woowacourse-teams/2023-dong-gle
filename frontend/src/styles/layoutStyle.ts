import { RuleSet, css } from 'styled-components';
import { theme } from './theme';

export const LAYOUT_STYLE = {
  padding: '1rem',
  border: `2px solid ${theme.color.gray13}`,
  gap: '0.4rem',
} as const;

export const SIDEBAR_STYLE = {
  width: '30rem',
} as const;

export const sidebarStyle = css`
  width: ${SIDEBAR_STYLE.width};
  border: ${LAYOUT_STYLE.border};
  border-radius: 8px;
  padding: 2rem;
  flex: 0 0 ${SIDEBAR_STYLE.width};
`;

export const genMainPageWidth = (
  isLeftSidebarOpen: boolean,
  isRightSidebarOpen: boolean,
): RuleSet<object> => {
  if (isLeftSidebarOpen === false && isRightSidebarOpen === false)
    return css`
      max-width: calc(100vw - (${LAYOUT_STYLE.padding}) * 2);
    `;
  if (
    (isLeftSidebarOpen === false && isRightSidebarOpen === true) ||
    (isLeftSidebarOpen === true && isRightSidebarOpen === false)
  )
    return css`
      max-width: calc(
        100vw - (${LAYOUT_STYLE.padding}) * 2 - (${SIDEBAR_STYLE.width}) - (${LAYOUT_STYLE.gap})
      );
    `;
  return css`
    max-width: calc(
      100vw - (${SIDEBAR_STYLE.width} + ${LAYOUT_STYLE.padding} + ${LAYOUT_STYLE.gap}) * 2
    );
  `;
};
