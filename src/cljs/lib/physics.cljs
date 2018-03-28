(ns lib.physics
  (:require [lib.vector :as v]))

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
