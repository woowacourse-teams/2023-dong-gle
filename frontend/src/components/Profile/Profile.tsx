import { DefaultUserProfileIcon } from 'assets/icons';
import { styled } from 'styled-components';

type Props = {
  name: string;
};

const Profile = ({ name }: Props) => {
  return (
    <S.Profile>
      <DefaultUserProfileIcon />
      {name}
    </S.Profile>
  );
};

export default Profile;

const S = {
  Profile: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    width: 30%;
    height: 100%;
    padding: 4rem 0;
    border-right: 1px solid ${({ theme }) => theme.color.gray5};
    background-color: ${({ theme }) => theme.color.gray2};
    font-size: 2rem;
    font-weight: bold;
  `,
};
