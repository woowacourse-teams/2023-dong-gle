import { keyframes } from 'styled-components';

export const rotation = keyframes`
  0% {
    transform: rotate(0deg);
  }   
  100% {
    transform: rotate(360deg);
  }
`;

export const slide = keyframes`
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0%);
    opacity: 1;
  }
`;
