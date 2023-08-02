import { categoryHandlers } from './handlers/category';
import { writingHandlers } from './handlers/writing';

export const handlers = [...writingHandlers, ...categoryHandlers];
