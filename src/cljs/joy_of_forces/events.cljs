(ns joy-of-forces.events
  (:require [re-frame.core :as re-frame]
            [joy-of-forces.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))
