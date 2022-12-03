
(fn string-to-chars [str]
  "returns the string as array of 'chars'"
  (let [chars {}]
    (for [index 1 (length str)]
      (table.insert chars (string.sub str index index)))
    chars))


(fn intersection [str1  ...]
  "calculates the intersection of n strings"
  (local str2 (if (> (length [...]) 1) ;; recursive call if multiple args
                  (intersection ...)
                  ...))
  (let [xs (do ;; can't treat strings like tables, convert
             (var array (string-to-chars str1))
             (table.sort array)
             array)
        ys (do
             (var array (string-to-chars str2))
             (table.sort array)
             array)
        zs {}]
    (var xi 1)
    (var yi 1)
    (while (and (<= xi (length xs)) (<= yi (length ys)))
      (let [x (. xs xi)
            y (. ys yi)]
        (if (= x y)
            (do (table.insert zs x) ;; same, increase both indices
                (set xi (+ xi 1))
                (set yi (+ yi 1)))
            (if (< x y) ;; different, increase only one index
                (set xi (+ xi 1))
                (set yi (+ yi 1))))))
    (accumulate [result "" ;; convert arrays back to strings
                 _ char (pairs zs)]
      (.. result char))))

(fn get-item-priority [item]
  "returns the priority of a given item"
  (let [item-value (string.byte item)]
    (- item-value
       (if (> item-value (string.byte "Z"))
           96 ;; lowercase
           38)))) ;; uppercase

(fn sum [table]
  "sums up each value from table"
  (accumulate [sum 0
               _ n (pairs table)]
    (+ sum n)))

;; task 1
(sum
 (icollect [line (io.lines "input")]
   (let [left (string.sub line 1 (/ (length line) 2))
         right (string.sub line (+ (/ (length line) 2) 1) (length line))]
     (-> (intersection left right)
         get-item-priority))))

;; task 2
(fn partition [xs n]
  "partitions an array into groups size n"
  (let [result {}]
    (for [index 1 (length xs) n]
      (local group {})
      (for [offset 0 (- n 1)]
        (table.insert group (. xs (+ index offset))))
      (table.insert result group))
    result))

(sum
 (icollect [_ group (pairs (partition
                            (icollect [line (io.lines "input")] line)
                            3))]
   (-> group
       table.unpack
       intersection
       get-item-priority)))
  
