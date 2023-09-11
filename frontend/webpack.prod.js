const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const Dotenv = require('dotenv-webpack');
const CompressionPlugin = require('compression-webpack-plugin');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'hidden-source-map',
  plugins: [new Dotenv({ path: './.env.production' }), new CompressionPlugin()],
  optimization: {
    splitChunks: {
      chunks: 'all',
    },
  },
});
