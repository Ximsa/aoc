(use '[clojure.string :only [split-lines]])

(defn update-fs
  "updates the filesystem with given command"
  [file-system command]
  (let [[x y z] (str/split command #" ")]
    (case [x y]
      ["$" "cd"] (if (= z "..") ;; change path
                   (update file-system :path rest) ;; go back 
                   (update file-system :path (partial cons z)))
      (if (re-matches #"[0-9]+" x)
        (update-in file-system (reverse (cons :file-size (:path file-system)))
                   ;; update directory file size
                   (fn [old] (+ (Integer/parseInt x) (if (nil? old) 0 old))))
        file-system)))) ;; otherwise keep as is

(defn parse
  "returns a directory representation"
  [file-name]
  (assoc
   (reduce update-fs
           {:path []}
           (->> file-name
                slurp
                split-lines
                (filter (partial not= ""))))
   :path ["/"]))

(defn dir-sizes
  "returns the directory sizes"
  [file-system]
  (map (fn [[key value]]
         (if (= key :file-size)
           value
           (dir-size (file-system key))))
       file-system))

(def sizes (filter (comp not zero?) (map (comp (partial apply +) flatten) (tree-seq seq? identity (dir-sizes ((parse "input") "/")))))) ;; it works, don't ask

;; part-1
(->> sizes
     (filter (partial > 100000))
     (apply +))
;; part-2
(let [total (apply max sizes)
      unused (- 70000000 total)
      requirement (- 30000000 unused)]
  (println total unused requirement)
  (->> sizes
       (filter (partial < requirement))
       (apply min)))
