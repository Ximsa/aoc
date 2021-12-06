(use '[clojure.string :only [split-lines split]])

(defn parse [file_name]
  (map
   (fn [line] (map
               #(Integer/parseInt %1)
               (split line #"[^\d]+")))
   (split-lines(slurp file_name))))
