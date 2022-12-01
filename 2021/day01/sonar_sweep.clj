(use '[clojure.string])

(defn parse [file_name]
  (map #(Integer/parseInt %1) (split-lines(slurp file_name))))

(defn sonar_sweep [sonar_data]
  (reduce (fn [acc strength]
            {:sum (+ (:sum acc) (if (> strength (:prev acc)) 1 0))
             :prev strength})
          {:sum 0 :prev Integer/MAX_VALUE}
          sonar_data))

(defn sonar_sweep_1 [file_name]
  (sonar_sweep (parse file_name)))

(defn sonar_sweep_2 [file_name]
  (sonar_sweep (map (partial apply +) (partition 3 1 (parse file_name)))))
