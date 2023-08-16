import { css } from 'styled-components';

export const HEADER_STYLE = {
  height: '5rem',
} as const;

export const LAYOUT_STYLE = {
  padding: '0 1rem 1rem 1rem',
  boxShadow: `rgba(31, 34, 37, 0.09) 0px 0px 0px 1px,
  rgba(0, 0, 0, 0.04) 0px 24px 48px,
  rgba(0, 0, 0, 0.02) 0px 4px 16px;`,
  gap: '0.8rem',
} as const;

export const SIDEBAR_STYLE = {
  width: '30rem',
} as const;

export const sidebarStyle = css`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  width: ${SIDEBAR_STYLE.width};
  box-shadow: ${LAYOUT_STYLE.boxShadow};
  border-radius: 8px;
  padding: 2rem;
  flex: 0 0 ${SIDEBAR_STYLE.width};
`;
