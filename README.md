# ads-api

The server-side component of [zodiactweets.com](https://github.com/rhodey/zodiactweets.com). This was just a small side project to investigate [RTB Ad Networks](https://en.wikipedia.org/wiki/Real-time_bidding) and practice React.js, now it's just published and kept alive for fun.

**I am fully aware that my TLS certs & keys are in this repo, I do not care**

## Setup
```
# useradd -m ads-api
# su ads-api
$ cd /home/ads-api
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
# <nginx>
```

## License

Copyright 2016 An Honest Effort LLC
