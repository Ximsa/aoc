(require hyrule [->])
(import re)

(defn parse [file-name]
  "returns a list of bounds"
  (let [lines (-> file-name ;; split file into lines
                  (open "r")
                  .read
                  .split)]
    (list (map (fn [line] ;; parse each line to [left-1 right-1 left-2 right-2]
                 (list (map int (re.split "[-,]" line))))
               lines))))

(defn part-1 [xs]
  "checks if one range is fully contained within another"
  (let [[left-1 right-1 left-2 right-2] xs]
    (or (<= left-1 left-2 right-2 right-1)
        (<= left-2 left-1 right-1 right-2))))

(defn part-2 [xs]
  "checks if ranges overlap"
  (let [[left-1 right-1 left-2 right-2] xs
        range-1 (set (range left-1 (+ 1 right-1)))
        range-2 (set (range left-2 (+ 1 right-2)))
        intersection (set.intersection range-1 range-2)]
    (!= 0 (len intersection))))

(sum (map part-2 (parse "input")))
