(ns joy-of-forces.planet
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]
            [lib.vector :as v]
            [lib.shapes :as s]
            [lib.physics :as p]
            [joy-of-forces.subs :as subs]))

(def width 800)
(def height 500)
(def unit 40)

(defn spawn []
  {:circle (p/create (q/random 0 width) (q/random 0 height) 0 0 0 0 30 30)
   :square (p/create (q/random 0 width) (q/random 0 height) 0 0 0 0 30 50)
   :triangle (p/create (q/random 0 width) (q/random 0 height) 0 0 0 0 30 20)})

(defn setup []
  (q/frame-rate 30)
  {:shapes (spawn)
   :attractor {:location {:x (/ width 2) :y (/ height 2)}
               :mass 100}})

(defn apply-forces [shape attractor]
  (let [k (first shape)
        obj (nth shape 1)
        mass (:mass obj)
        force (p/compute-gravitation obj attractor)
        a (p/apply-force mass force)
        v (v/limit (v/add (:velocity obj) a) (:topspeed obj))
        l (v/add (:location obj) v)]
    [k (p/create-obj l v (v/create 0 0) (:topspeed obj) (:mass obj))]))

(defn update-attractor [attr]
  (let [l (:location attr)
        loc (if (q/mouse-pressed?)
              (v/create (q/mouse-x) (q/mouse-y))
              l)]
    (assoc attr :location loc)))

(defn updt [state]
  (let [updated-shapes (mapv #(apply-forces % (:attractor state)) (:shapes state))
        updated-attractor (update-attractor (:attractor state))]
    {:shapes updated-shapes
     :attractor updated-attractor}))

(defn draw [state]
  (let [shapes (:shapes state)
        ci (nth (get shapes 0) 1)
        sq (nth (get shapes 1) 1)
        tr (nth (get shapes 2) 1)
        attr (:attractor state)]
    (q/background 150)
    (s/draw-star attr)
    (s/draw-circle (:x (:location ci)) (:y (:location ci)))
    (s/draw-square (:x (:location sq)) (:y (:location sq)))
    (s/draw-triangle (:x (:location tr)) (:y (:location tr)))))

(q/defsketch sketch
  :setup setup
  :draw draw
  :update updt
  :host "planet"
  ;;:features [:no-start]
  :size [width height]
  :middleware [m/fun-mode])
