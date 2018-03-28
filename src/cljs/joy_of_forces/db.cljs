(ns joy-of-forces.db)

(def default-db
  {:wind {:x 0.1 :y 0.0}
   :gravity {:x 0.0 :y 0.2}
   :friction {:c 0.1}
   :liquid {:c 0.4}
   :active-forces #{}})
