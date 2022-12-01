(use '[clojure.string :only [split split-lines]])

(defn parse [file_name]
  (map (fn [line] (map #(split %1 #"\s+") (split line #"\s*\|\s*"))) (split-lines (slurp file_name))))

(defn part1 [line] (count (filter #(some (partial = (count %1)) [2 3 4 7]) (last line))))


