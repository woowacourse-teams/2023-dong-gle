import { useToastContext } from '../../contexts/ToastProvider';
import type { Props as ToastProps } from '../../components/@common/Toast/Toast';

export const useToast = () => {
  const { setToastList } = useToastContext();

  const showToast = (newToast: Partial<ToastProps>) => {
    setToastList(({ uniqueKey, toasts }) => ({
      uniqueKey: uniqueKey + 1,
      toasts: [...toasts, { toastId: uniqueKey, ...newToast }],
    }));
  };

  const removeToast = (targetToastId: number) => {
    setToastList(({ uniqueKey, toasts }) => ({
      uniqueKey,
      toasts: toasts.filter(({ toastId }) => toastId !== targetToastId),
    }));
  };

  return { show: showToast, remove: removeToast };
};
