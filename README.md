# ads-api

lol, aggregate.

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
