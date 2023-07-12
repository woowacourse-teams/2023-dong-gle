import { createGlobalStyle } from 'styled-components';
import { reset } from './reset';

const GlobalStyle = createGlobalStyle`
	${reset}

	html {
		font-size: 10px;
	}
`;

export default GlobalStyle;
