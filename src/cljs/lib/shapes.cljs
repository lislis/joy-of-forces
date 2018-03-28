(ns lib.shapes
  (:require [quil.core :as q]))

(def unit 50)

(defn draw-circle [x y]
  (q/fill 145 157 206)
  (q/stroke 30)
  (q/ellipse x y unit unit))

(defn draw-square [x y]
  (let [y1 (- y (/ unit 2))]
    (q/fill 191 67 128)
    (q/stroke 30)
    (q/rect x y1 unit unit)))

(defn draw-triangle [x y]
  (let [x1 x
        y1 (+ (/ unit 2) y)
        x2 (+ x unit)
        y2 y1
        x3 (+ (/ unit 2) x)
        y3 (- y (/ unit 2))]
    (q/fill 252 253 242)
    (q/stroke 50)
    (q/triangle x1 y1 x2 y2 x3 y3)))
