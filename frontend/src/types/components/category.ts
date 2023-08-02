import { GetWritingResponse } from 'types/apis/writings';

export type CategoryWriting = Omit<GetWritingResponse, 'content'>;
