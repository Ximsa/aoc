(def sample (->> "sample"
                slurp
                read-string))
(def input (->> "input"
                slurp
                read-string))

(defn compare-lists [xs ys]
  (case [(empty? xs) (empty? ys)]
    [false false] (let [result (decide-order (first xs) (first ys))]
                    (if (zero? result)
                      (decide-order (rest xs) (rest ys))
                      result))
    [true false] 1 ;; right order if xs empty first
    [false true] -1 ;; wrong order if ys empty first
    [true true] 0)) ;; equal

(defn compare-integers [x y] (compare y x))

(defn compare-integer-list [x y]
  (case [(number? x) (number? y)]
    [true false] (decide-order [x] y)
    [false true] (decide-order x [y])))

(defn decide-order
  ([[xs ys]] (decide-order xs ys))
  ([xs ys] (case [(number? xs) (number? ys)]
             [false false] (compare-lists xs ys)
             [true false] (decide-order [xs] ys)
             [false true] (decide-order xs [ys])
             [true true] (compare-integers xs ys))))
;;part 1
(apply + (map-indexed
          (fn [index value] (if (> (decide-order value) 0) (inc index) 0))
          (partition 2 input)))
;; part 2
(let [sorted-signals (reverse (sort decide-order (conj input [[6]] [[2]])))]
  (* (inc (.indexOf sorted-signals [[6]]))
     (inc (.indexOf sorted-signals [[2]]))))
