(use '[clojure.string :only [split-lines]])

(defn parse
  "returns the amount of calories each elf has"
  [file-name]
  (->> file-name
       (slurp)
       (split-lines)
       (partition-by (partial = ""))
       (filter (partial not= [""]))
       (map (comp
             (partial apply +)
             (partial map #(Integer/parseInt %1))))))

(apply max (parse "input")) ;; 1

(->> "input" ;; 2
     (parse)
     (sort)
     (take-last 3)
     (apply +))
