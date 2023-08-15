export type DeletedWriting = {
  id: number;
  title: string;
};

export type GetDeletedWritingsResponse = { writings: DeletedWriting[] };
