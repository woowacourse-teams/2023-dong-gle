import { useCallback, useEffect } from 'react';
import { ComponentPropsWithoutRef } from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components';
import { CloseIcon } from 'assets/icons';

type Props = {
  isOpen: boolean;
  canBackdropClose?: boolean;
  canEscKeyClose?: boolean;
  hasCloseButton?: boolean;
  closeModal: () => void;
} & ComponentPropsWithoutRef<'dialog'>;

const Modal = ({
  isOpen = true,
  canBackdropClose = true,
  canEscKeyClose = true,
  hasCloseButton = true,
  closeModal,
  children,
  ...rest
}: Props) => {
  const onKeyDownEscape = useCallback(
    (event: KeyboardEvent) => {
      if (event.key !== 'Escape') return;
      closeModal();
    },
    [closeModal],
  );

  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden';
    }

    return () => {
      document.body.style.overflow = 'auto';
    };
  }, [isOpen]);

  useEffect(() => {
    if (isOpen && canEscKeyClose) {
      window.addEventListener('keydown', onKeyDownEscape);
    }

    return () => {
      window.removeEventListener('keydown', onKeyDownEscape);
    };
  }, [isOpen, canEscKeyClose, onKeyDownEscape]);

  return createPortal(
    <S.ModalWrapper>
      {isOpen && (
        <>
          <S.Backdrop onClick={canBackdropClose ? closeModal : undefined} />
          <S.Content {...rest}>
            {hasCloseButton && (
              <S.CloseButton type='button' onClick={closeModal}>
                <CloseIcon width={24} height={24} />
              </S.CloseButton>
            )}
            {children}
          </S.Content>
        </>
      )}
    </S.ModalWrapper>,
    document.body,
  );
};

export default Modal;

const S = {
  ModalWrapper: styled.div`
    position: relative;
    z-index: 9999;
  `,
  Backdrop: styled.div`
    position: fixed;
    inset: 0;
    background: ${({ theme }) => theme.color.modalBackdrop};
  `,
  Content: styled.dialog`
    position: fixed;
    inset: 50% auto auto 50%;
    display: flex;
    justify-content: center;
    min-width: 20vw;
    max-height: 80vh;
    overflow: auto;
    padding: 2.5rem;
    border: none;
    border-radius: 8px;
    background-color: ${({ theme }) => theme.color.gray1};
    transform: translate(-50%, -50%);
  `,
  CloseButton: styled.button`
    position: absolute;
    inset: 2.5rem 2.5rem auto auto;
  `,
};
