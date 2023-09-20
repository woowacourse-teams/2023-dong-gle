export const hasDefinedField = <TestType>(arg: any, testField: string): arg is TestType => {
  return arg[testField] !== undefined;
};
