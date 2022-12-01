(use '[clojure.string])

(defn parse [file_name]
  (map (fn [[direction steps]] [direction (Integer/parseInt steps)])
       (partition 2 (split (slurp file_name) #"\s+"))))

(defn dive_1 [data]
  (apply * (reduce
            (fn [[x y] [direction steps]]
              (case direction
                "up" [x (- y steps)]
                "down" [x (+ y steps)]
                "forward" [(+ x steps) y]
                [x y]))
            [0 0]
            data)))

(defn dive_2 [data]
  (apply * (take 2 (reduce
            (fn [[x y aim] [direction steps]]
              (case direction
                "up" [x y (- aim steps)]
                "down" [x y (+ aim steps)]
                "forward" [(+ x steps) (+ y (* aim steps)) aim]
                [x y aim]))
            [0 0 0]
            data))))
