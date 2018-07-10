const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const SriPlugin = require("webpack-subresource-integrity");
const LiveReloadPlugin = require("webpack-livereload-plugin");

const env = process.env.NODE_ENV;

let extraHtml = "";

if (env === "development") {
  extraHtml =
    extraHtml + '<script src="http://localhost:35729/livereload.js"></script>';
}

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
      template: "./src/index.html",
      extraHtml: extraHtml
    }),
    new SriPlugin({
      hashFuncNames: ["sha256", "sha384", "sha512"],
      enabled: env === "production"
    }),
    new LiveReloadPlugin()
  ]
};
