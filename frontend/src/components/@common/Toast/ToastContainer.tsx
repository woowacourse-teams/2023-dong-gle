import { styled } from 'styled-components';
import Toast from './Toast';
import { useToastContext } from '../../../contexts/ToastProvider';
import { createPortal } from 'react-dom';
import { useToast } from '../../../hooks/@common/useToast';

type Props = {
  maxShowingCount?: number;
};

const ToastContainer = ({ maxShowingCount = 3 }: Props) => {
  const { toastList } = useToastContext();
  const { remove } = useToast();

  return createPortal(
    <S.Container>
      {[...toastList.toasts].splice(0, maxShowingCount).map(({ toastId, ...options }) => {
        return <Toast key={toastId} toastId={toastId} {...options} onClose={remove} />;
      })}
    </S.Container>,
    document.body,
  );
};

export default ToastContainer;

const S = {
  Container: styled.div`
    position: fixed;
    left: 50%;
    bottom: 5rem;
    transform: translateX(-50%);
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,
};
