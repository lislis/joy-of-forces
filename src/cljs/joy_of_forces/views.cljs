(ns joy-of-forces.views
  (:require [re-frame.core :as re-frame]
            [joy-of-forces.subs :as subs]
            ))

(defn is-checked? [actives key]
  (if (= (get actives key) nil)
    false
    true))

(defn main-panel []
  (let [active-forces (re-frame/subscribe [::subs/activate-force])
        wind (re-frame/subscribe [::subs/wind])
        gravity (re-frame/subscribe [::subs/gravity])
        friction (re-frame/subscribe [::subs/friction])
        liquid (re-frame/subscribe [::subs/liquid])]
    [:div
     [:h1 "The joy of simulating forces"]
     [:div.panel
      [:h2.panel-opener "Force controls"]
      [:div.control-panel
       [:input {:type "checkbox" :id "wind-check"
                :checked (is-checked? @active-forces :wind)
                :on-change #(re-frame/dispatch [:activate-force :wind])}]
       [:label {:for "wind-check"} "Activate wind"]
       [:div.control-drawer
        [:p "Wind: "
         [:span.value (:x @wind)]]
        [:button
         {:on-click #(re-frame/dispatch [:increase-wind 0.1])}
         "+"]
        [:button
         {:on-click #(re-frame/dispatch [:decrease-wind 0.1])}
         "-"]]
       ]
      [:div.control-panel
       [:input {:type "checkbox" :id "fric-check"
                :checked (is-checked? @active-forces :friction)
                :on-change #(re-frame/dispatch [:activate-force :friction])}]
       [:label {:for "fric-check"} "Activate friction"]
       [:div.control-drawer
        [:p "Friction: "
         [:span.value (:c @friction)]]
        [:button
         {:on-click #(re-frame/dispatch [:increase-friction 0.1])}
         "+"]
        [:button
         {:on-click #(re-frame/dispatch [:decrease-friction 0.1])}
         "-"]]]
      [:div.control-panel
       [:input {:type "checkbox" :id "gravity-check"
                :checked (is-checked? @active-forces :gravity)
                :on-change #(re-frame/dispatch [:activate-force :gravity])}]
       [:label {:for "gravity-check"} "Activate gravity"]
       [:div.control-drawer
        [:p "Gravity: "
         [:span.value (:y @gravity)]]
        [:button
         {:on-click #(re-frame/dispatch [:increase-gravity 0.1])}
         "+"]
        [:button
         {:on-click #(re-frame/dispatch [:decrease-gravity 0.1])}
         "-"]]]
      [:div.control-panel
       [:input {:type "checkbox" :id "liquid-check"
                :checked (is-checked? @active-forces :liquid)
                :on-change #(re-frame/dispatch [:activate-force :liquid])}]
       [:label {:for "liquid-check"} "Activate liquid (drag force)"]
       [:div.control-drawer
        [:p "Drag: "
         [:span.value (:c @liquid)]]
        [:button
         {:on-click #(re-frame/dispatch [:increase-drag 0.1])}
         "+"]
        [:button
         {:on-click #(re-frame/dispatch [:decrease-drag 0.1])}
         "-"]]]]]))
