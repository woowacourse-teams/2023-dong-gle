import { keyframes } from 'styled-components';

const rotation = keyframes`
  0% {
    transform: rotate(0deg);
  }   
  100% {
    transform: rotate(360deg);
  }
`;

const linearToRight = keyframes`
  from {
    width: 0%;
  }

  99% {
    border-radius: 0 0 0 8px;
  }

  to {
    width: 100%;
    border-radius: 0 0 8px 8px;
  }
`;

const slideToLeft = keyframes`
  from {
    transform: translateX(100%);
    opacity: 0;
  }
  to {
    transform: translateX(0%);
    opacity: 1;
  }
`;

const slideToRight = keyframes`
  from {
    transform: translateX(-100%);
    opacity: 0;
  }
  to {
    transform: translateX(0%);
    opacity: 1;
  }
`;

const slideToUp = keyframes`
  from {
    transform: translateY(100%);
    opacity: 0;
  }
  to {
    transform: translateY(0%);
    opacity: 1;
  }
`;

const slideToDown = keyframes`
  from {
    transform: translateY(-100%);
    opacity: 0;
  }
  to {
    transform: translateY(0%);
    opacity: 1;
  }
`;

export { rotation, linearToRight, slideToDown, slideToLeft, slideToRight, slideToUp };
