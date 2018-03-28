(ns lib.vector)

(declare normalize)

(defn create
  "creates a vector"
  [x y]
  {:x x :y y})

(defn add
  "adds two vectors together"
  [vec1 vec2]
  (let [x (+ (:x vec1) (:x vec2))
        y (+ (:y vec1) (:y vec2))]
    (create x y)))

(defn sub
  "subtracts two vectors"
  [vec1 vec2]
  (let [x (- (:x vec1) (:x vec2))
        y (- (:y vec1) (:y vec2))]
    (create x y)))

(defn mult
  "multiplies vector by scalar"
  [vec scalar]
  (let [x (* (:x vec) scalar)
        y (* (:y vec) scalar)]
    (create x y)))

(defn div
  "divides vector by scalar"
  [vec scalar]
  (let [x (/ (:x vec) scalar)
        y (/ (:y vec) scalar)]
    (create x y)))

(defn mag
  "returns magnitude of a vector"
  [vec]
  (let [x (:x vec)
        y (:y vec)]
    (js/Math.sqrt (+ (* x x) (* y y)))))

(defn normalize
  "normalizes vector to 1"
  [vec]
  (let [m (mag vec)]
    (if (not (= m 0))
      (div vec m)
      vec)))

(defn limit
  "limit magnitude of vector, returns vector"
  [vec limit]
  (if (< (mag vec) limit)
    vec
    (mult (normalize vec) limit)))
