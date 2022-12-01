(use '[clojure.string :only (split-lines)])

(defn transpose [m]
  (apply map list m))

(defn parse [file_name]
  (map
   (fn [line] (map
               #(Character/digit %1 10)
               (char-array line)))
   (split-lines(slurp file_name))))

(defn binary_to_decimal [bits]
  (first(reduce (fn [[result power] digit]
                  [(+ result (* power digit)) (* power 2)])
                [0 1]
                (reverse bits))))

(defn invert [bits]
  (map
   (fn [bit] (mod (inc bit) 2))
   bits))

(defn most_used [data]
  (if (> (apply + data) (/ (count data) 2)) 1 0 ))

(defn diagnostic_1 [data]
  (def gamma (map most_used (transpose data)))
  (* (binary_to_decimal gamma) (binary_to_decimal (invert gamma))))

(defn diagnostic [trafo data n]
  (if (= (count data) 1)
    (first data)
    (diagnostic trafo
                (filter
                 (fn [a] (= (nth a n) (trafo (most_used (nth (transpose data) n)))))
                 data)
                (inc n))))

(defn diagnostic_2 [data]
  (* (binary_to_decimal(diagnostic identity data 0))
  (binary_to_decimal (diagnostic #(mod (inc %1) 2) data 0))))
