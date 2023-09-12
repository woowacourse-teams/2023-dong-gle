import { useEffect } from 'react';
import Prism from 'prismjs';
import 'prismjs/themes/prism.css';

const prismLanguageFromCodeTagRegex = /<code class="language-(\w+)">/g;

const useCodeHighlight = (htmlDOMString?: string) => {
  useEffect(() => {
    if (!htmlDOMString) return;
    const importPrism = async () => {
      const languages = Array.from(htmlDOMString.matchAll(prismLanguageFromCodeTagRegex)).map(
        (match) => match[1],
      );

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
