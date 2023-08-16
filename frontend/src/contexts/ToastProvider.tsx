import { ReactNode, createContext, useContext, useState } from 'react';
import type { Props as ToastProps } from '../components/@common/Toast/Toast';

type ToastItem = {
  toastId: number;
} & Partial<ToastProps>;

type ToastList = {
  uniqueKey: number;
  toasts: ToastItem[];
};

const ToastContext = createContext<{
  toastList: ToastList;
  setToastList: React.Dispatch<React.SetStateAction<ToastList>>;
} | null>(null);

export const useToastContext = () => {
  const value = useContext(ToastContext);

  if (!value) throw Error('ToastContext가 존재하지 않습니다.');

  return value;
};

type Props = {
  children: ReactNode;
};

const ToastProvider = ({ children }: Props) => {
  const [toastList, setToastList] = useState<ToastList>({
    uniqueKey: 1,
    toasts: [],
  });

  const value = { toastList, setToastList };

  return <ToastContext.Provider value={value}>{children}</ToastContext.Provider>;
};

export default ToastProvider;
