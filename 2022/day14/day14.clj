(use '[clojure.string :only [split split-lines]])
(use '[clojure.set :only [union]])
(defn sgn [n] (Integer/signum n))
(def v-add (partial map +))

(defn parse [file-name]
  (->> file-name
      slurp
      split-lines
      (map (fn [line] (partition 2 (map #(Integer/parseInt %1) (split line #" -> |,")))))))


(defn create-segment [[[ax ay] [bx by]]]
  (let [direction_x (sgn (- bx ax))
        direction_y (sgn (- by ay))
        range_x (conj (range ax bx direction_x) ax bx)
        range_y (conj (range ay by direction_y) ay by)]
    (set (for [x range_x
               y range_y]
           [x y]))))

(defn create-segments [lines]
  (set
   (apply union
          (map (fn [line]
                 (let [segments (map create-segment (partition 2 1 line))]
                   (apply union segments)))
               lines))))

(defn simulate-sand [sand rocks max-y]
  (if (> (second sand) max-y)
    ;; rocks ;; sand fell out of the world ;; part 1
    (set (union rocks #{sand})) ;; part 2
    (let [down (v-add sand [0 1])
          left (v-add sand [-1 1])
          right (v-add sand [1 1])
          [down? left? right?] (map (partial (complement contains?) rocks)
                                    [down left right])]
      (cond
        down? (recur down rocks max-y)
        left? (recur left rocks max-y)
        right? (recur right rocks max-y)
        :else (set (union rocks #{sand}))))))

(defn run-simulation
  ([rocks max-y] (run-simulation rocks max-y 0))
  ([rocks max-y iteration]
   (let [new-rocks (simulate-sand [500 0] rocks max-y)]
     (if (= new-rocks rocks)
       iteration
       (recur new-rocks max-y (inc iteration)))))) ;; this time recur is actually needed to prevent stack overflow

(let [contents (parse "input")
      max-y (->> contents
                 flatten
                 rest
                 (take-nth 2)
                 (apply max))
      rocks (create-segments contents)]
  (run-simulation rocks max-y))
