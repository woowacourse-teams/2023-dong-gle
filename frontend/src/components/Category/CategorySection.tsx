import { useGenerateCategoryTuple } from 'hooks/useGenerateCategoryTuple';
import { styled } from 'styled-components';

const CategorySection = () => {
  const generateCategoryTuple = useGenerateCategoryTuple();

  return <S.Section>{/* <Accordion accordionContents={generateCategoryTuple()} /> */}</S.Section>;
};

export default CategorySection;

const S = {
  Section: styled.section``,
};
