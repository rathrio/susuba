# susuba

This application provides a MongoDB backend that can be accessed via a JSON API. A frontend is in development at the [susuba-web-ui project][1].
[1]: http://github.com/jugglinghobo/susuba-web-ui

## Prerequisites

### Leiningen

You will need [Leiningen][2] 1.7.0 or above installed.

[2]: https://github.com/technomancy/leiningen

### MongoDB

Install MongoDB. [Here's how][3].

[3]: http://docs.mongodb.org/manual/installation/

## Running

Start MongoDB first:

    mongod

then run the application:

    lein run

### Foreman

This application has a Procfile, so if you're using [foreman][4] you can also 
run it with:

    foreman start

[4]: http://github.com/ddollar/foreman

## License

Copyright © 2013 FIXME
