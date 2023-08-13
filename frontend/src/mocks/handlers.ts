import { categoryHandlers } from './handlers/category';
import { errorHandlers } from './handlers/error';
import { loginHandlers } from './handlers/login';
import { trashHandlers } from './handlers/trash';
import { writingHandlers } from './handlers/writing';

export const handlers = [
  ...writingHandlers,
  ...categoryHandlers,
  ...errorHandlers,
  ...trashHandlers,
  ...loginHandlers,
];
