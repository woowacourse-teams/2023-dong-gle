import { useEffect } from 'react';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';

const useCodeHighlight = (htmlDOMString?: string) => {
  useEffect(() => {
    if (!htmlDOMString) return;
    const importPrism = async () => {
      const parser = new DOMParser();
      const doc = parser.parseFromString(htmlDOMString, 'text/html');

      const codeElements = doc.querySelectorAll('code[class^="language-"]');
      const languages = Array.from(codeElements)
        .map((element) => {
          const match = element.className.match(/language-(\w+)/);
          return match ? match[1] : null;
        })
        .filter(Boolean);

      await Promise.all(
        languages.map((language) => import(`prismjs/components/prism-${language}`)),
      );
    };

    const highlightCode = () => {
      Prism.highlightAll();
    };

    importPrism().then(highlightCode);
  }, [htmlDOMString]);
};

export default useCodeHighlight;
