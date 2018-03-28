# The joy of simulating forces

A little interactive app to toy around with simulated forces.

Want to do the same? Read [Daniel Schiffman's book 'Nature of Code'](http://natureofcode.com/book/).


Using [quil-cljs](http://quil.info/) for Processing and [re-frame](https://github.com/Day8/re-frame) for extra interactivenes and UI.

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
