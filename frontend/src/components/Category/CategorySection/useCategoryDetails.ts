import { useQuery } from '@tanstack/react-query';
import { getCategories } from 'apis/category';
import { GetCategoryDetailResponse } from 'types/apis/category';
import { CategoryWriting } from 'types/components/category';

export const useCategoryDetails = (
  selectedCategoryId: number | null,
  writings: CategoryWriting[] | null,
) => {
  const { data: categories } = useQuery(['categories'], getCategories);

  // const [categoryDetails, setCategoryDetails] = useState<GetCategoryDetailResponse[] | undefined>(
  //   categories?.categories.map((category) => ({
  //     id: category.id,
  //     categoryName: category.categoryName,
  //     writings: null,
  //   })),
  // );

  const categoryDetails: GetCategoryDetailResponse[] | undefined = categories?.categories.map(
    (category) => ({
      id: category.id,
      categoryName: category.categoryName,
      writings: null,
    }),
  );

  // const updateCategoryDetails = (selectedCategoryId: number, writings: CategoryWriting[]) => {
  //   // 카테고리 클릭
  //   setCategoryDetails((prevDetails: GetCategoryDetailResponse[] | null) => {
  //     return prevDetails
  //       ? prevDetails.map((detail) =>
  //           detail.id === selectedCategoryId ? { ...detail, writings } : { ...detail },
  //         )
  //       : null;
  //   });
  // };

  // useEffect(() => {
  //   if (!categories) return;

  //   const initCategoryDetails = () => {
  //     // 동글 첫 진입 시, 카테고리 목록 데이터 만드는 함수
  //     return categories.categories.map((category) => ({
  //       id: category.id,
  //       categoryName: category.categoryName,
  //       writings: null,
  //     }));
  //   };

  //   const updateAddedCategory = (prevDetails: GetCategoryDetailResponse[]) => {
  //     // 카테고리가 추가됐을 때, 동기화하는 함수
  //     return categories.categories.map((category) => {
  //       const prevDetail = prevDetails.find((detail) => detail.id === category.id);
  //       const addedCategory = {
  //         id: category.id,
  //         categoryName: category.categoryName,
  //         writings: null,
  //       };

  //       return prevDetail ? prevDetail : addedCategory;
  //     });
  //   };

  //   setCategoryDetails((prevDetails) => {
  //     return prevDetails ? updateAddedCategory(prevDetails) : initCategoryDetails();
  //   });
  // }, [categories]);

  // useEffect(() => {
  //   const updateCategoryDetails = (selectedCategoryId: number, writings: CategoryWriting[]) => {
  //     // 카테고리 클릭
  //     setCategoryDetails((prevDetails: GetCategoryDetailResponse[] | null) => {
  //       return prevDetails
  //         ? prevDetails.map((detail) =>
  //             detail.id === selectedCategoryId ? { ...detail, writings } : { ...detail },
  //           )
  //         : null;
  //     });
  //   };

  //   if (selectedCategoryId && writings) updateCategoryDetails(selectedCategoryId, writings);
  // }, [writings]);

  return { categoryDetails };
};
