export const dateFormatter = (date: Date, format: 'YYYY.MM.DD.') => {
  switch (format) {
    case 'YYYY.MM.DD.':
      return new Intl.DateTimeFormat('ko-KR').format(new Date(date));
  }
};
