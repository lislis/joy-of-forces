(ns joy-of-forces.db)

(def width 800)
(def height 500)

(def default-db
  {:wind {:x 0.1 :y 0.0}
   :gravity {:x 0.0 :y 0.1}
   :friction {:c 0.1 :n 1.0}
   :liquid {:c 1.9
            :x 0
            :y (- height 200)
            :w width
            :h (/ height 2)}
   :active-forces #{}})
