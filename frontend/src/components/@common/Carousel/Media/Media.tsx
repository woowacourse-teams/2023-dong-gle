import styled from 'styled-components';

export type Media = {
  type:
    | 'image/jpeg'
    | 'image/png'
    | 'image/webp'
    | 'image/avif'
    | 'image/gif'
    | 'video/mp4'
    | 'video/webm';

  src: string;
  alt?: string;
};

type Props = {
  media: Media;
};

const Media = ({ media }: Props) => {
  const { type, src, alt } = media;

  return (
    <S.Media>
      {type.startsWith('video/') ? (
        <video controls autoPlay playsInline loop width='100%' height='100%'>
          <source src={src} type={type} />
          비디오를 지원하지 않는 브라우저입니다.
        </video>
      ) : (
        <picture>
          <source srcSet={src} type={type} />
          <img src={src} alt={alt ?? '캐러셀의 컨텐츠'} width='100%' height='100%' />
        </picture>
      )}
    </S.Media>
  );
};

export default Media;

const CommonMediaStyles = `
  width: 100%;
  height: 100%;
`;

const S = {
  Media: styled.div`
    ${CommonMediaStyles}

    picture {
      ${CommonMediaStyles}
    }

    img {
      ${CommonMediaStyles}
    }

    video {
      ${CommonMediaStyles}
    }
  `,
};
