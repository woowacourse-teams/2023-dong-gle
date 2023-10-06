export type DeletedWriting = {
  id: number;
  title: string;
  categoryId: number;
};

export type DeleteWritingRequest = {
  writingIds: number[];
  isPermanentDelete: boolean;
};

export type GetDeletedWritingsResponse = { writings: DeletedWriting[] };
