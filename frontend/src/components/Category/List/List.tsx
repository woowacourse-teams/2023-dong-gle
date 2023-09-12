import Accordion from 'components/@common/Accordion/Accordion';
import { useCategories } from './useCategories';
import Item from '../Item/Item';

const List = () => {
  const { categories } = useCategories();

  if (!categories) return null;

  return (
    <Accordion>
      {categories.map((category, index) => {
        return (
          <Item
            key={category.id}
            categoryId={category.id}
            categoryName={category.categoryName}
            isDefaultCategory={Boolean(index === 0)}
          />
        );
      })}
    </Accordion>
  );
};

export default List;
