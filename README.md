# The joy of simulating forces

A little interactive app to toy around with simulated forces.
Based on Processing/ quil-cljs.


Want to do the same? Read [Daniel Schiffman's book 'Nature of Code'](http://natureofcode.com/book/).

A [re-frame](https://github.com/Day8/re-frame) application designed to ... well, that part is up to you.

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
