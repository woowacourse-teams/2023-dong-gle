const color = {
  primary: '#405178',
  primaryHover: '#2e3b57',

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

export type Color = keyof typeof theme.color;

export const theme = {
  color,
};
