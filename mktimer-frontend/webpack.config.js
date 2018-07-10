const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const SriPlugin = require("webpack-subresource-integrity");

const env = process.env.NODE_ENV;

module.exports = {
  mode: env || "development",
  entry: "./src/index.js",
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader"
        }
      }
    ]
  },
  output: {
    path: path.resolve(__dirname, "dist"),
    filename: "bundle.js",
    crossOriginLoading: "anonymous"
  },
  plugins: [
    new HtmlWebpackPlugin({
      hash: true,
      filename: "index.html",
      template: "./src/index.html"
    }),
    new SriPlugin({
      hashFuncNames: ["sha256", "sha384", "sha512"],
      enabled: env === "production"
    })
  ]
};
