import { RuleSet, css } from 'styled-components';
import { theme } from './theme';

export const HEADER_STYLE = {
  height: '3rem',
} as const;

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
