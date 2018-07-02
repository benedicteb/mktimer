#!/bin/sh
git config --global user.email "builds@travis-ci.com"
git config --global user.name "Travis CI"

if [ "$TRAVIS_BRANCH" = "master" ]; then
  mvn_version=$(mvn -q \
      -Dexec.executable="echo" \
      -Dexec.args='${project.version}' \
      --non-recursive \
    org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)

  tag="travistest-v$mvn_version"

  echo $mvn_version
  echo $tag

  git tag -a $tag -m "Released version $version"
  git push origin $tag
fi
