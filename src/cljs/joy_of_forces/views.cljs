(ns joy-of-forces.views
  (:require [re-frame.core :as re-frame]
            [joy-of-forces.subs :as subs]
            ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div "Hello from " @name]))
