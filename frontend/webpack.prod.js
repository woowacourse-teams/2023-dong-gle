const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const Dotenv = require('dotenv-webpack');
const CompressionPlugin = require('compression-webpack-plugin');
const ImageMinimizerPlugin = require('image-minimizer-webpack-plugin');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'hidden-source-map',
  plugins: [new Dotenv({ path: './.env.production' }), new CompressionPlugin()],
  module: {
    rules: [
      {
        test: /\.(jpe?g|png)$/i,
        type: 'asset',
      },
    ],
  },
  optimization: {
    minimizer: [
      new ImageMinimizerPlugin({
        minimizer: {
          implementation: ImageMinimizerPlugin.sharpMinify,
          options: {
            encodeOptions: {
              webp: {
                lossless: true,
              },
              avif: {
                lossless: true,
              },
              png: {},
            },
          },
        },
      }),
    ],
    splitChunks: {
      chunks: 'all',
    },
  },
});
