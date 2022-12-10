(use '[clojure.string :only [split split-lines]])

(defn parse
  "returns the amount of calories each elf has"
  [file-name]
  (->> file-name
       slurp
       split-lines
       (map (fn [xs] (split xs #" ")))))

(defn update [x [operation value]]
  (case operation
    "noop" [x]
    "addx" [x (+ x (Integer/parseInt value))]))
    
(defn cpu-cycles [x [operation & operations]]
  (if (nil? operation)
    '()
    (let [x (update x operation)]
      (concat x (cpu-cycles (last x) operations)))))

(def registers (cons 1 (cpu-cycles 1 (parse "input"))))

;; part 1
(apply + (map
          (fn [n] (* n (nth registers (dec n))))
          [20 60 100 140 180 220]))

;; part2
(def part-2
  (let [lines (partition 40 registers)]
    (map (fn [line]
           (map-indexed (fn [index value]
                          (if (<= (Math/abs (- index value)) 1) \# \.))
                        line))
         lines)))

(map (partial apply str) part-2)
