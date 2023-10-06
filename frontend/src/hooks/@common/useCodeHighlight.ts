import { useEffect } from 'react';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';
import 'prismjs/plugins/autoloader/prism-autoloader.js';

// webpack copyWebpackPlugin 이용해 prismjs/components/경로에 언어모듈 저장
Prism.plugins.autoloader.languages_path = '/prismjs/components/';

const useCodeHighlight = (htmlDOMString?: string) => {
  useEffect(() => {
    if (!htmlDOMString) return;

    const highlightCode = () => {
      Prism.highlightAll();
    };

    highlightCode();
  }, [htmlDOMString]);
};

export default useCodeHighlight;
