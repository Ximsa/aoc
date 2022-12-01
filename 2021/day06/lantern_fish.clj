(use '[clojure.string :only [split]])

(defn parse [file_name]
  (reduce
   (fn [result age]
     (update result age inc))
   [0 0 0 0 0 0 0 0 0]
   (map #(Integer/parseInt %1)
        (split (slurp file_name) #"[^\d]+"))))

(defn tick [[x0 x1 x2 x3 x4 x5 x6 x7 x8]]
  [x1 x2 x3 x4 x5 x6 (+ x0 x7) x8 x0])

(defn calc [file_name n]
  (apply + (last (take (inc n) (iterate tick (parse file_name))))))
