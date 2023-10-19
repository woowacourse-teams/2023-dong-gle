import { AnimationEventHandler, MouseEventHandler, useState } from 'react';
import { styled } from 'styled-components';
import AnimationDiv from 'components/@common/AnimationDiv/AnimationDiv';
import { CloseIcon } from 'assets/icons';
import { linearToRight } from 'styles/animation';

export const ToastTheme = ['light', 'colored'] as const;
export type ToastTheme = (typeof ToastTheme)[number];

export const ToastType = ['plain', 'info', 'success', 'warning', 'error'] as const;
export type ToastType = (typeof ToastType)[number];

type HideToast = MouseEventHandler<HTMLButtonElement> & AnimationEventHandler<HTMLDivElement>;

export type Props = {
  toastId: number;
  theme?: ToastTheme;
  type?: ToastType;
  duration?: number;
  hasCloseButton?: boolean;
  hasProgressBar?: boolean;
  message?: string;
  onClose: (toastId: number) => void;
};

const Toast = ({
  toastId,
  theme = 'light',
  type = 'plain',
  duration = 3000,
  hasCloseButton = false,
  hasProgressBar = false,
  message,
  onClose,
}: Props) => {
  const [isVisible, setIsVisible] = useState(true);

  const hideToast: HideToast = () => setIsVisible(false);

  const removeToast = (toastId: number) => {
    onClose(toastId);
  };

  return (
    <AnimationDiv isVisible={isVisible} onDisappear={() => removeToast(toastId)}>
      <S.ToastContainer $toastTheme={theme} $type={type}>
        <S.Content>
          <span>{message}</span>
          {hasCloseButton && (
            <S.CloseButton onClick={hideToast}>
              <CloseIcon width={16} height={16} />
            </S.CloseButton>
          )}
        </S.Content>
        <S.ProgressBar $type={type} $hasProgressBar={hasProgressBar}>
          <S.PercentageBar $type={type} $duration={duration} onAnimationEnd={hideToast} />
        </S.ProgressBar>
      </S.ToastContainer>
    </AnimationDiv>
  );
};

export default Toast;

const S = {
  ToastContainer: styled.div<{
    $toastTheme: ToastTheme;
    $type: ToastType;
  }>`
    position: relative;
    white-space: nowrap;
    width: fit-content;
    padding: 1.4rem 2.7rem;
    background-color: ${({ theme, $type, $toastTheme }) =>
      $toastTheme === 'light' ? theme.color.gray1 : theme.toastColor[$type].background};
    border: 1px solid ${({ theme, $type }) => theme.toastColor[$type].border};
    border-radius: 8px;
    box-shadow:
      #00000014 0px 12px 24px -4px,
      rgba(0, 0, 0, 0.04) 0px 8px 16px -4px;
    font-size: 1.4rem;
    font-weight: 500;
    overflow: hidden;
  `,
  Content: styled.div`
    display: flex;
    align-items: center;
    gap: 1.2rem;
  `,
  CloseButton: styled.button`
    padding: 0.4rem;
    border-radius: 4px;

    &:hover {
      background-color: ${({ theme }) => theme.color.gray5};
    }
  `,
  ProgressBar: styled.div<{ $type: ToastType; $hasProgressBar: boolean }>`
    visibility: ${({ $hasProgressBar }) => ($hasProgressBar ? 'visible' : 'hidden')};

    content: '';

    position: absolute;
    bottom: 0;
    left: 0;
    height: 5px;
    width: 100%;

    border-radius: 0 0 8px 8px;
    background-color: ${({ theme }) => theme.color.gray5};
  `,
  PercentageBar: styled.div<{ $type: ToastType; $duration: number }>`
    height: 100%;
    border-radius: 0 0 0 8px;
    background-color: ${({ theme, $type }) => theme.toastColor[$type].border};
    animation: ${linearToRight} ${({ $duration }) => `${$duration}ms`} linear;
  `,
};
