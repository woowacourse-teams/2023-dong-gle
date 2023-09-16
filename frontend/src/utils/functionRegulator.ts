export const throttle = <T extends (...args: any[]) => any>(func: T, delay: number) => {
  let timer: ReturnType<typeof setTimeout> | null;

  return (...args: Parameters<T>) => {
    if (timer) return;

    timer = setTimeout(() => {
      timer = null;
      func(...args);
    }, delay);
  };
};
