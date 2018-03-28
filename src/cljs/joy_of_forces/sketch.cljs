(ns joy-of-forces.sketch
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [lib.vector :as v]
            [lib.shapes :as s]
            [lib.physics :as p]))

(defn spawn []
  {:circle (p/create 250 250 0 0 0 0 5 30)
   :square (p/create 350 250 0 0 0 0 5 50)
   :triangle (p/create 470 250 0 0 0 0 5 20)})

(defn setup []
  {:shapes (spawn)})

(defn updt [state]
  state)

(defn draw [state]
  (let [shapes (:shapes state)
        ci (:circle shapes)
        sq (:square shapes)
        tr (:triangle shapes)]
    (s/draw-circle (:x (:location ci)) (:y (:location ci)))
    (s/draw-square (:x (:location sq)) (:y (:location sq)))
    (s/draw-triangle (:x (:location tr)) (:y (:location tr)))))

(q/defsketch sketch
  :setup setup
  :draw draw
  :update updt
  :host "canvas"
  ;;:features [:no-start]
  :size [800 500]
  :middleware [m/fun-mode])
