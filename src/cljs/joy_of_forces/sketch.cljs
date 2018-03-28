(ns joy-of-forces.sketch
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [lib.vector :as v]
            [lib.shapes :as s]
            [lib.physics :as p]
            [re-frame.core :as re-frame]
            [joy-of-forces.subs :as subs]))

(defn is-active? [actives key]
  (if (= (get actives key) nil)
    false
    true))

(defn spawn []
  {:circle (p/create 250 250 0 0 0 0 5 30)
   :square (p/create 350 250 0 0 0 0 5 50)
   :triangle (p/create 470 250 0 0 0 0 5 20)})

(defn setup []
  {:shapes (spawn)})

(defn apply-direct-force [mass force]
  (v/div force mass))

(defn compute-forces [list wind gravity friction liquid]
  (let [empty-force (v/create 0 0)
        active-wind? (is-active? list :wind)
        force1 (if (= active-wind? true)
                 {:wind (v/add empty-force wind)}
                 {:wind empty-force})]
    (conj {} force1)
    ;;(js/console.log (:x force1) (:y force1))
    force1))

(defn apply-forces [shape forces]
  (let [k (first shape)
        obj (nth shape 1)
        ;;a (reduce v/add (vals forces))
        a (:wind forces)
        v (v/limit (v/add (:velocity obj) a) (:topspeed obj))
        l (v/add (:location obj) v)]
    ;;(js/console.log shape)
    [k (p/create-obj l v (v/create 0 0) (:topspeed obj) (:mass obj))]))

(defn updt [state]
  (let [active-forces (re-frame/subscribe [::subs/activate-force])
        wind (re-frame/subscribe [::subs/wind])
        gravity (re-frame/subscribe [::subs/gravity])
        friction (re-frame/subscribe [::subs/friction])
        liquid (re-frame/subscribe [::subs/liquid])
        forces (compute-forces @active-forces @wind @gravity @friction @liquid)
        updated-shapes (mapv #(apply-forces % forces) (:shapes state))]
    ;;(js/console.log updated-shapes)
    {:shapes updated-shapes}))

(defn draw [state]
  (let [shapes (:shapes state)
        ci (nth (get shapes 0) 1)
        sq (nth (get shapes 1) 1)
        tr (nth (get shapes 2) 1)]
    (q/background 220)
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
