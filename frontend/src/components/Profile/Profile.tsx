import { DefaultUserProfileIcon } from 'assets/icons';
import DeleteAccountModal from 'components/Modal/DeleteAccountModal.tsx/DeleteAccountModal';
import { MAX_WIDTH } from 'constants/style';
import { useModal } from 'hooks/@common/useModal';
import { css, styled } from 'styled-components';

type Props = {
  name: string;
};

const Profile = ({ name }: Props) => {
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <>
      <S.Profile>
        <DefaultUserProfileIcon />
        {name}
        <S.DeleteAccountButton onClick={openModal}>탈퇴하기</S.DeleteAccountButton>
      </S.Profile>
      <DeleteAccountModal isOpen={isOpen} closeModal={closeModal} />
    </>
  );
};

export default Profile;

const generateResponsiveStyle = {
  profile: () => {
    return css`
      @media (max-width: ${MAX_WIDTH.mobileLarge}) {
        justify-content: center;
        width: 100%;
        height: initial;
        border-bottom: 1px solid ${({ theme }) => theme.color.gray5};
      }
    `;
  },
};

const S = {
  Profile: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    width: 25%;
    height: 100%;
    padding: 4rem 0;
    border-right: 1px solid ${({ theme }) => theme.color.gray5};
    background-color: ${({ theme }) => theme.color.gray2};
    font-size: 2rem;
    font-weight: bold;

    ${() => generateResponsiveStyle.profile()}
  `,

  DeleteAccountButton: styled.button`
    padding: 0.1rem 0;
    color: ${({ theme }) => theme.color.gray7};
    border-bottom: 1px solid ${({ theme }) => theme.color.gray6};

    &:hover {
      color: ${({ theme }) => theme.color.gray8};
      border-bottom: 1px solid ${({ theme }) => theme.color.gray7};
    }
  `,
};
