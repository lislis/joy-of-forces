(ns lib.physics
  (:require [lib.vector :as v]))

(def bouncyness-wall -0.5)
(def bouncyness-floor -0.5)
(def unit 40)

(defn create-obj [l v a topspeed mass]
  {:location l
   :velocity v
   :acceleration a
   :topspeed topspeed
   :mass mass})

(defn create [x y vx vy ax ay topspeed mass]
  (create-obj
   (v/create x y)
   (v/create vx vy)
   (v/create ax ay)
   topspeed
   mass))

(defn bounce-wall [l w h]
  (let [x (cond (> (:x l) (- w unit)) (- w unit)
                (< (:x l) 0) 0
                :else (:x l))
        y (if (> (:y l) h)
            h
            (:y l))]
    (v/create x y)))

(defn bounce-vel [v l w h]
  (let [x (cond
            (> (:x l) (- w unit)) (* bouncyness-wall (:x v))
            (< (:x l) 0) (* bouncyness-wall (:x v))
            :else (:x v))
        y (if (> (:y l) h)
            (* bouncyness-floor (:y v))
            (:y v))]
    (v/create x y)))

(defn apply-direct-force [mass force]
  (v/div force mass))
