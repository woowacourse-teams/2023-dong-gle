import { HttpStatus } from 'constants/apis/http';
import React, { Component, ErrorInfo, ReactNode } from 'react';
import { HttpError } from 'utils/apis/HttpError';

export type ErrorBoundaryFallbackProps = {
  status?: number;
  title: string;
  message: string;
};

type Props = {
  children: ReactNode;
  fallback?: React.FunctionComponent<ErrorBoundaryFallbackProps>;
  onReset?: VoidFunction;
};

type State = {
  error: Error | null;
};

export class ErrorBoundary extends Component<Props, State> {
  // getDerivedStateFrom~ → render → componentDid~ 순서로 동작
  constructor(props: Props) {
    super(props);
    this.state = { error: null };
  }

  // 다음 렌더링에서 폴백 UI가 보이도록 상태를 업데이트
  static getDerivedStateFromError(error: Error) {
    return { error: error || null };
  }

  // You can render any custom fallback UI
  render() {
    const { fallback } = this.props;

    if (this.state.error) {
      const error = this.state.error;
      if (error instanceof HttpError && fallback) {
        return React.createElement(fallback, {
          status: error.statusCode,
          title: '',
          message: error.message,
        });
      }
    }

    return this.props.children;
  }

  // 에러 리포팅 서비스에 에러를 기록
  componentDidCatch(error: Error, info: ErrorInfo) {
    // TODO::Sentry
  }
}
