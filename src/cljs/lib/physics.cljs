(ns lib.physics
  (:require [lib.vector :as v]
            [quil.core :as q]))

(def bouncyness-wall -0.5)
(def bouncyness-floor -0.5)
(def unit 40)
(def magical-unit 25)
(def G 0.4)

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
  (let [half-w (- w unit)
        half-h (- h magical-unit)
        x (cond (> (:x l) half-w) half-w
                (< (:x l) 0) 0
                :else (:x l))
        y (cond (> (:y l) half-h) half-h
                (< (:y l) magical-unit) magical-unit
                :else (:y l))]
    (v/create x y)))

(defn bounce-vel [v l w h]
  (let [half-w (- w unit)
        half-h (- h magical-unit)
        x (cond
            (> (:x l) half-w) (* bouncyness-wall (:x v))
            (< (:x l) 0) (* bouncyness-wall (:x v))
            :else (:x v))
        y (cond
            (> (:y l) half-h) (* bouncyness-floor (:y v))
            (< (:y l) magical-unit) (* bouncyness-floor (:y v))
            :else (:y v))]
    (v/create x y)))

(defn is-inside? [shape liquid]
  (let [m (:location shape)
        l liquid]
    (if (and (> (:x m) (:x l))
             (< (:x m) (+ (:x l) (:w l)))
             (> (:y m) (:y l))
             (< (:y m) (+ (:y l) (:h l))))
      true
      false)))

(defn apply-force [mass force]
  (v/div force mass))

(defn is-active? [actives key]
  (if (= (get actives key) nil)
    false
    true))

(defn compute-wind [mass force active-list]
  (let [active? (is-active? active-list :wind)
        wind (if (= active? true)
               (apply-force mass force)
               (v/create 0 0))]
    wind))

(defn compute-friction [mass velocity force active-list]
  (let [active? (is-active? active-list :friction)
        fric-mag (* (:c force) (:n force))
        friction (if (= active? true)
                   (apply-force mass (v/mult (v/normalize (v/mult velocity -1)) fric-mag))
                   (v/create 0 0))]
    friction))

(defn compute-gravity [mass force active-list]
  (let [active? (is-active? active-list :gravity)
        scaled-grav-y (* (:y force) mass)
        gravity (if (= active? true)
                  (apply-force mass (v/create (:x force) scaled-grav-y))
                  (v/create 0 0))]
    gravity))

(defn compute-liquid [obj force active-list]
  (let [mass (:mass obj)
        velocity (:velocity obj)
        location (:location obj)
        active? (is-active? active-list :liquid)
        inside? (is-inside? obj force)
        drag (if (and (= active? true)
                      (= inside? true))
               (let [speed (v/mag velocity)
                     drag-mag (* (:c force) speed speed)
                     drag (-> velocity
                              (v/mult -1)
                              (v/normalize)
                              (v/mult drag-mag))]
                 (apply-force mass drag))
               (v/create 0 0))
        ]
    drag))

(defn compute-gravitation [obj attractor]
  (let [force (v/sub (:location attractor) (:location obj))
        distance (q/constrain (v/mag force) 20 50)
        norm-force (v/normalize force)
        strength (/ (* G (:mass attractor) (:mass obj))
                    (* distance distance))
        scaled-force (v/mult force strength)]
    scaled-force))
