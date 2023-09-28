import { categoryHandlers } from './handlers/category';
import { trashHandlers } from './handlers/trash';
import { writingHandlers } from './handlers/writing';
import { loginHandlers } from './handlers/login';
import { memberHandlers } from './handlers/member';
import { connectionsHandlers } from './handlers/connections';
import { authHandlers } from './handlers/auth';

export const handlers = [
  ...writingHandlers,
  ...categoryHandlers,
  ...loginHandlers,
  ...trashHandlers,
  ...memberHandlers,
  ...connectionsHandlers,
  ...authHandlers,
];
