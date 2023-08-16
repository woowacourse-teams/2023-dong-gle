export type DeletedWriting = {
  id: number;
  title: string;
  categoryId: number;
};

export type GetDeletedWritingsResponse = { writings: DeletedWriting[] };
