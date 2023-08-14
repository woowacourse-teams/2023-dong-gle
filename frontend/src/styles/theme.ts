const color = {
  primary: '#405178',
  primaryHover: '#2e3b57',

  tistory: '#FF5A4A',
  medium: '#000000',

  modalBackdrop: '#00000059',

  red1: '#fff1f0',
  red2: '#ffccc7',
  red3: '#ffa39e',
  red4: '#ff7875',
  red5: '#ff4d4f',
  red6: '#f5222d',
  red7: '#cf1322',
  red8: '#a8071a',
  red9: '#820014',
  red10: '#5c0011',

  gray1: '#ffffff',
  gray2: '#fafafa',
  gray3: '#f5f5f5',
  gray4: '#f0f0f0',
  gray5: '#d9d9d9',
  gray6: '#bfbfbf',
  gray7: '#8c8c8c',
  gray8: '#595959',
  gray9: '#434343',
  gray10: '#262626',
  gray11: '#1f1f1f',
  gray12: '#141414',
  gray13: '#000000',
} as const;

const toastColor = {
  plain: {
    border: '#BEBEBE',
    background: '#fafafa',
  },
  info: {
    border: '#48c1b5',
    background: '#f6fff9',
  },
  success: {
    border: '#9dc0ee',
    background: '#f5f9ff',
  },
  warning: {
    border: '#f7d9a4',
    background: '#fff8ec',
  },
  error: {
    border: '#f4b0a1',
    background: '#fff5f3',
  },
};

export type Color = keyof typeof theme.color;

export const theme = {
  color,
  toastColor,
};
