(use 'clojure.string)

(defn parse [file_name]
  (map
   (fn [line] (map
               #(Character/digit %1 10)
               (char-array line)))
   (split-lines(slurp file_name))))

(defn binary_to_decimal [bits]
  (first (reduce
          (fn [[result power] [digit]]
            [(+ result (* power digit)) (* power 2)])
          [0 1]
          bits)))

(defn transpose [m]
  (apply map list m))

(defn diagnostic_1 [data]
  (def gamma (map #(if (> (apply + %1) (/ (count %1) 2)) 1 0)(transpose (parse "input"))))
  (* ))
