
(fn intersection [str1 ...]
  "calculates the instersection of n strings"
  (local str2 (if (> (length [...]) 1) ;; recursive call if multiple args
                  (intersection ...)
                  ...))
  (-> (fcollect [index 1 (length str1)]
        (let [char (string.sub str1 index index)]
          (if (string.find str2 char)
              char
              "")))
      table.concat))
          

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
  
