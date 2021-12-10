(use '[clojure.string :only [split split-lines]])

(defn parse [file_name]
  (map char-array (split-lines (slurp file_name))))

(defn matches [a b]
  (or
   (and (= a \() (= b \)))
   (and (= a \[) (= b \]))
   (and (= a \{) (= b \}))
   (and (= a \<) (= b \>))))

(defn score [a]
  (case a
    \( 1 \[ 2 \{ 3 \< 4 ;; part 2
    \) 3 \] 57 \} 1197 \> 25137)) ;; part 1

(defn score_incomplete [brackets]
  (reduce 
   (fn [total current] (+(* 5 total) current))
   0
   (map score brackets)))

(defn check_line [default_action line]
  (reduce (fn [acc bracket]
            (if (some (partial = bracket) [\{ \( \[ \<])
              (cons bracket acc) ;; push opening parens to stack
              (if (matches (first acc) bracket)
                (drop 1 acc) ;; pop stack if parens match
                (reduced (default_action bracket))))) ;; else return score
          '()
          line))

(apply + (filter number? (map (partial check_line score)
                              (parse "input")))) ;; task 1

(def task_2_result
  (filter (partial not= 0) (sort (map
                                  (comp score_incomplete (partial check_line (fn [a] [])))
                                  (parse "input")))));; task 2
(nth task_2_result (/ (count task_2_result) 2)) ;; median
