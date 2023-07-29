import Accordion from 'components/@common/Accordion/Accordion';
import { useCategories } from 'hooks/useCategories';
import { styled } from 'styled-components';
import Category from './Category';
import WritingsInCategory from './WritingsInCategory';
import { useWritingsInCategory } from 'hooks/useWritingsInCategory';

const CategorySection = () => {
  const { categories } = useCategories();
  const { getWritings, writings } = useWritingsInCategory();

  if (!categories) return null;

  return (
    <S.Section>
      <Accordion>
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
      </Accordion>
    </S.Section>
  );
};

export default CategorySection;

const S = {
  Section: styled.section``,
};
