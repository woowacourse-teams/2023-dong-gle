import type { StorybookConfig } from '@storybook/react-webpack5';
import TsconfigPathsPlugin from 'tsconfig-paths-webpack-plugin';

const config: StorybookConfig = {
  stories: ['../src/**/*.mdx', '../src/**/*.stories.@(js|jsx|ts|tsx)'],
  addons: [
    '@storybook/addon-links',
    '@storybook/addon-essentials',
    '@storybook/addon-interactions',
  ],
  staticDirs: ['../public'],
  framework: {
    name: '@storybook/react-webpack5',
    options: {},
  },
  docs: {
    autodocs: true,
  },
  webpackFinal: async (config) => {
    if (config.resolve) {
      config.resolve.plugins = [
        ...(config.resolve.plugins || []),
        new TsconfigPathsPlugin({
          extensions: config.resolve.extensions,
        }),
      ];
    }

    if (!config.module || !config.module.rules) {
      return config;
    }

    config.module.rules = [
      ...config.module.rules.map((rule) => {
        if (!rule || rule === '...') {
          return rule;
        }

        if (rule.test && /svg/.test(String(rule.test))) {
          return { ...rule, exclude: /\.svg$/i };
        }
        return rule;
      }),
      {
        test: /\.svg$/,
        use: ['@svgr/webpack', 'url-loader'],
      },
    ];

    return config;
  },
};

export default config;
