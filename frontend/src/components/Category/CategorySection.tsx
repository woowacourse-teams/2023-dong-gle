// import Accordion from 'components/@common/Accordion/Accordion';
import { useCategories } from 'hooks/useCategories';
import { styled } from 'styled-components';
import Category from './Category';
import WritingsInCategory from './Writings';
import { useCategoryDetail } from 'hooks/useCategoryDetail';
import Button from 'components/@common/Button/Button';

const CategorySection = () => {
  const { categories } = useCategories();
  const { categoryId, writings, getWritings } = useCategoryDetail();

  if (!categories) return null;

  return (
    <S.Section>
      {categories.map((category) => {
        return (
          <>
            <Category id={category.id} categoryName={category.categoryName} />
            <Button onClick={() => getWritings(category.id)}>글 목록 조회</Button>
            {writings && categoryId === category.id ? (
              <WritingsInCategory writings={writings} />
            ) : null}
          </>
        );
      })}
      {/* <Accordion>
        {categories.map((category) => {
          return (
            <Accordion.Item>
              <Accordion.Title onClick={() => getWritings(category.id)}>
                <Category id={category.id} categoryName={category.categoryName} />
              </Accordion.Title>
              <Accordion.Panel>
                {writings && <WritingsInCategory writings={writings} />}
              </Accordion.Panel>
            </Accordion.Item>
          );
        })}
      </Accordion> */}
    </S.Section>
  );
};

export default CategorySection;

const S = {
  Section: styled.section``,
};
