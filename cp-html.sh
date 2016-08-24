#!/bin/bash
ADS_HTML_DIR=$PWD/src/main/html


#####################
# zodiactweets.com
#####################
ZODIAC_SRC_DIR=~/dev/advertising/zodiactweets.com
ZODIAC_TARGET_DIR=$ADS_HTML_DIR/zodiactweets.com

cd $ZODIAC_SRC_DIR
git checkout production
npm run build

cp index.html $ZODIAC_TARGET_DIR/index.html
cp bundle.js $ZODIAC_TARGET_DIR/bundle.js
cp bundle.css $ZODIAC_TARGET_DIR/bundle.css
cp img/* $ZODIAC_TARGET_DIR/img/

git checkout master


#####################
# stolen.holiday
#####################
STOLEN_SRC_DIR=~/dev/advertising/stolen.holiday
STOLEN_TARGET_DIR=$ADS_HTML_DIR/stolen.holiday

cd $STOLEN_SRC_DIR
cp index.html $STOLEN_TARGET_DIR/index.html
