type dateFormate = 'YYYY.MM.DD.' | 'YYYY/MM/DD HH:MM';

export const dateFormatter = (date: Date, format: dateFormate) => {
  switch (format) {
    case 'YYYY.MM.DD.':
      return new Intl.DateTimeFormat('ko-KR').format(new Date(date));
    case 'YYYY/MM/DD HH:MM':
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, '0');
      const day = String(d.getDate()).padStart(2, '0');
      const hours = String(d.getHours()).padStart(2, '0');
      const minutes = String(d.getMinutes()).padStart(2, '0');

      return `${year}/${month}/${day} ${hours}:${minutes}`;
  }
};
