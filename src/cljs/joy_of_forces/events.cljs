(ns joy-of-forces.events
  (:require [re-frame.core :as re-frame]
            [joy-of-forces.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))

(defn inc-wind [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        wind (:wind db)
        new-wind (+ (:x wind) value)]
    {:db (assoc-in db [:wind :x] new-wind)}))

(defn dec-wind [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        wind (:wind db)
        new-wind (- (:x wind) value)]
    {:db (assoc-in db [:wind :x] new-wind)}))

(defn inc-grav [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        grav (:gravity db)
        new-grav (+ (:y grav) value)]
    {:db (assoc-in db [:gravity :y] new-grav)}))

(defn dec-grav [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        grav (:gravity db)
        new-grav (- (:y grav) value)]
    {:db (assoc-in db [:gravity :y] new-grav)}))

(defn inc-fric [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        fric (:friction db)
        new-fric (+ (:c fric) value)]
    {:db (assoc-in db [:friction :c] new-fric)}))

(defn dec-fric [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        fric (:friction db)
        new-fric (- (:c fric) value)]
    {:db (assoc-in db [:friction :c] new-fric)}))

(defn inc-drag [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        drag (:liquid db)
        new-drag (+ (:c drag) value)]
    {:db (assoc-in db [:liquid :c] new-drag)}))

(defn dec-drag [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        drag (:liquid db)
        new-drag (- (:c drag) value)]
    {:db (assoc-in db [:liquid :c] new-drag)}))

(defn activate-force [coeffects event]
  (let [value (second event)
        db (:db coeffects)
        active (:active-forces db)
        new-active (if (= (get active value) nil)
                     (conj active value)
                     (disj active value))]
    {:db (assoc-in db [:active-forces] new-active)}))

(re-frame/reg-event-fx
 :increase-wind
 inc-wind)

(re-frame/reg-event-fx
 :decrease-wind
 dec-wind)

(re-frame/reg-event-fx
 :increase-gravity
 inc-grav)

(re-frame/reg-event-fx
 :decrease-gravity
 dec-grav)

(re-frame/reg-event-fx
 :increase-friction
 inc-fric)

(re-frame/reg-event-fx
 :decrease-friction
 dec-fric)

(re-frame/reg-event-fx
 :increase-drag
 inc-drag)

(re-frame/reg-event-fx
 :decrease-drag
 dec-drag)

(re-frame/reg-event-fx
 :activate-force
 activate-force)
