(ns lib.shapes
  (:require [quil.core :as q]
            [lib.physics :as p :refer [is-active?]]))

(def unit 50)
(def TWO_PI 6.2831855)

(defn draw-circle [x y]
  (let [x (+ x (/ unit 2))]
    (q/fill 145 157 206)
    (q/stroke 30)
    (q/stroke-weight 4)
    (q/ellipse x y unit unit)))

(defn draw-square [x y]
  (let [y1 (- y (/ unit 2))]
    (q/fill 191 67 128)
    (q/stroke 30)
    (q/stroke-weight 4)
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
    (q/stroke-weight 4)
    (q/triangle x1 y1 x2 y2 x3 y3)))

(defn draw-liquid [liq active-list]
  (let [active? (is-active? active-list :liquid)]
    (if (= active? true)
      (do
        (q/no-stroke)
        (q/fill 146 198 236)
        (q/rect (:x liq) (:y liq) (:w liq) (:h liq))))))

(defn draw-star [obj]
  (let [x (+ (:x (:location obj)) (/ unit 2))]
    (q/fill 200)
    (q/stroke-weight 4)
    (q/ellipse x (:y (:location obj)) 100 100)))
