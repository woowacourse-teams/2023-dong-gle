import { createGlobalStyle } from 'styled-components';
import { reset } from './reset';
import { MAX_WIDTH } from 'constants/style';

const GlobalStyle = createGlobalStyle`
	${reset}

	html {
		font-size: 10px;
		@media (max-width: ${MAX_WIDTH.tablet}) {
			font-size: 9px;
    }
		@media (max-width: ${MAX_WIDTH.mobileMedium}) {
			font-size: 8.5px;
    }
	}

	* {
		font-family: 'Spoqa Han Sans Neo', 'sans-serif';
	}
`;

export default GlobalStyle;
