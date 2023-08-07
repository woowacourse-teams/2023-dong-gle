import { categoryHandlers } from './handlers/category';
import { errorHandlers } from './handlers/error';
import { writingHandlers } from './handlers/writing';

export const handlers = [...writingHandlers, ...categoryHandlers, ...errorHandlers];
