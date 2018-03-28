(ns joy-of-forces.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::wind
 (fn [db]
   (:wind db)))

(re-frame/reg-sub
 ::gravity
 (fn [db]
   (:gravity db)))

(re-frame/reg-sub
 ::friction
 (fn [db]
   (:friction db)))

(re-frame/reg-sub
 ::liquid
 (fn [db]
   (:liquid db)))

(re-frame/reg-sub
 ::activate-force
 (fn [db]
   (:active-forces db)))
