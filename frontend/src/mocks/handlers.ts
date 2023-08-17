import { categoryHandlers } from './handlers/category';
import { errorHandlers } from './handlers/error';
import { trashHandlers } from './handlers/trash';
import { writingHandlers } from './handlers/writing';
import { loginHandlers } from './handlers/login';
import { memberHandlers } from './handlers/member';
import { connectionsHandlers } from './handlers/connections';

export const handlers = [
  ...writingHandlers,
  ...categoryHandlers,
  ...errorHandlers,
  ...loginHandlers,
  ...trashHandlers,
  ...memberHandlers,
  ...connectionsHandlers,
];
