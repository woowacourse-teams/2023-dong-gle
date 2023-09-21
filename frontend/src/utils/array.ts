export const isSameArray = <Element>(arr1: Element[], arr2: Element[]) => {
  return arr2.length === arr1.length && arr2.every((value, idx) => value === arr1[idx]);
};
