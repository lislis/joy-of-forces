(ns joy-of-forces.sketch
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [lib.vector :as v]
            [lib.shapes :as s]
            [lib.physics :as p]
            [re-frame.core :as re-frame]
            [joy-of-forces.subs :as subs]))

(def width 800)
(def height 500)
(def unit 40)

(defn spawn []
  {:circle (p/create 130 250 0 0 0 0 5 30)
   :square (p/create 250 250 0 0 0 0 5 50)
   :triangle (p/create 370 250 0 0 0 0 5 20)})

(defn setup []
  {:shapes (spawn)})

(defn apply-forces [shape]
  (let [active-forces (re-frame/subscribe [::subs/activate-force])
        wind (re-frame/subscribe [::subs/wind])
        gravity (re-frame/subscribe [::subs/gravity])
        friction (re-frame/subscribe [::subs/friction])
        liquid (re-frame/subscribe [::subs/liquid])

        k (first shape)
        obj (nth shape 1)
        mass (:mass obj)
        velocity (:velocity obj)

        calc-wind (p/compute-wind mass @wind @active-forces)
        calc-friction (p/compute-friction mass velocity @friction @active-forces)
        calc-gravity (p/compute-gravity mass @gravity @active-forces)
        calc-liquid (p/compute-liquid obj @liquid @active-forces)

        a (-> (v/add calc-wind calc-friction)
              (v/add calc-gravity)
              (v/add calc-liquid))
        v1 (v/limit (v/add velocity a) (:topspeed obj))
        l1 (v/add (:location obj) v1)

        v (p/bounce-vel v1 l1 width height)
        l (p/bounce-wall l1 width height)]
    [k (p/create-obj l v (v/create 0 0) (:topspeed obj) (:mass obj))]))

(defn updt [state]
  (let [updated-shapes (mapv #(apply-forces %) (:shapes state))]
    {:shapes updated-shapes}))

(defn draw [state]
  (let [shapes (:shapes state)
        ci (nth (get shapes 0) 1)
        sq (nth (get shapes 1) 1)
        tr (nth (get shapes 2) 1)
        liquid (re-frame/subscribe [::subs/liquid])
        active-forces (re-frame/subscribe [::subs/activate-force])]
    (q/background 220)
    (s/draw-liquid @liquid @active-forces)
    (s/draw-circle (:x (:location ci)) (:y (:location ci)))
    (s/draw-square (:x (:location sq)) (:y (:location sq)))
    (s/draw-triangle (:x (:location tr)) (:y (:location tr)))))

(q/defsketch sketch
  :setup setup
  :draw draw
  :update updt
  :host "canvas"
  ;;:features [:no-start]
  :size [width height]
  :middleware [m/fun-mode])
