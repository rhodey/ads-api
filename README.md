# ads-api

The server-side component of [zodiactweets.com](https://github.com/rhodey/zodiactweets.com). This was just a small side project to investigate [RTB Ad Networks](https://en.wikipedia.org/wiki/Real-time_bidding) and practice React.js, now it's just published and kept alive for fun.

## Setup
```
# useradd -m ads-api
# su ads-api
$ mkdir /home/ads-api/src
$ cd /home/ads-api/src
$ git clone https://github.com/rhodey/ads-api
$ cd ads-api
$ mvn package
```

## Configure
```
$ cp config/example-config.yml config/config.yml
```

## Test
```
$ java -jar target/ads-api-0.1.jar server config/config.yml
```

## Install
```
# cp config/ads-api.conf /etc/init/ads-api.conf
# initctl reload-configuration
# start ads-api
```

## License

Copyright 2016 An Honest Effort LLC
