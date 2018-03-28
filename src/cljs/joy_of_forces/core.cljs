(ns joy-of-forces.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [joy-of-forces.events :as events]
            [joy-of-forces.views :as views]
            [joy-of-forces.config :as config]
            [joy-of-forces.sketch :as sketch]
            ;;[quil.core :as q :include-macros true]
            ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root)
  (sketch/sketch))
