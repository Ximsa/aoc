(use '[clojure.string :only [split-lines split]])

(defn sgn [n] (Integer/signum n))

(def v-add (partial map +))
(def v-diff (partial map -))
(def v-sgn (partial map sgn))
(defn v-length [v] (apply max (map #(Math/abs %1) v)))

(def tail-visited (atom #{'(0 0)}))

(defn parse
  "returns a list of moves"
  [file-name]
  (->> file-name
       slurp
       split-lines
       (map (fn [xs] (repeat (Integer/parseInt (apply str (drop 2 xs))) (first xs))))
       flatten
       (map (fn [direction] (case direction
                              \L [-1 0]
                              \R [1 0]
                              \U [0 1]
                              \D [0 -1]
                              [0 0])))))

(defn propagate-rope-segment [head succ]
  (let [distance (v-diff head succ)
        succ (if (> (v-length distance) 1)
               (v-add succ (v-sgn distance))
               succ)]
    succ))

(defn propagate-rope-segments [[x1 x2 & xs]]
  (if (nil? x2)
    (do ;; tail position
      (swap! tail-visited #(conj %1 x1))
      '())
    (let [x2 (propagate-rope-segment x1 x2)]
      (cons x2 (propagate-rope-segments (cons x2 xs))))))

(defn move-rope-segment [xs direction]
  (let [x (v-add (first xs) direction)]
    (cons x (propagate-rope-segments xs))))

(reduce move-rope-segment
        (repeat 10 [0 0]) ;; change to 2 segments for part 1
        (parse "input"))

(count @tail-visited)
