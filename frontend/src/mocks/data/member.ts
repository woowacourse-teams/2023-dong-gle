export const member = {
  id: 1,
  name: '아커',
  tistory: {
    isConnected: false,
    blogName: '아커의 개발 블로그',
  },
  notion: {
    isConnected: false,
  },
  medium: {
    isConnected: false,
  },
};

export const isConnected = (platform: 'tistory' | 'notion' | 'medium') => {
  return member[platform].isConnected;
};

export const connect = (platform: 'tistory' | 'notion' | 'medium') => {
  member[platform].isConnected = true;
};

export const disconnect = (platform: 'tistory' | 'notion' | 'medium') => {
  member[platform].isConnected = false;
};
