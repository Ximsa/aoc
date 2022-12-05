(chdir "2022/day05") ;; :shrug: 

(use-modules (ice-9 textual-ports))
(use-modules (ice-9 regex))

(define (inc x) (+ x 1))
(define (dec x) (- x 1))
(define (id x) x)
(define (reduce f y xs)
  "fold left"
  (if (null? xs)
      y
      (reduce f (f y (car xs)) (cdr xs))))

(define (nth list n)
  "return the n-th element of a list"
  (if (= 0 n)
      (car list)
      (nth (cdr list) (dec n))))

(define (take list n)
  "returns the first n elements of a list"
  (if (= 0 n)
      '()
      (cons (car list) (take (cdr list) (dec n)))))

(define (drop list n)
  "returns the elements after the first n elements of a list"
  (if (= 0 n)
      list
      (drop (cdr list) (dec n))))

(define (replace-nth list n item)
  "returns a list with the n-th element replaced by item"
  (if (= 0 n)
      (cons item (cdr list))
      (cons (car list) (replace-nth (cdr list) (dec n) item))))

;; actual aoc specific 
(define (parse file-name)
  "returns crane commands"
  (let* ((contents (call-with-input-file file-name get-string-all))
	 (lines (map match:substring (list-matches "[^\n]+" contents)))
	 (commands (map (lambda (line) ;; for each line
			  (map (lambda (numbers) (dec (string->number (match:substring numbers))))
			       (list-matches "[0-9]+" line))) ;; extract numbers
			lines)))
    commands))

(define sample-crates (map string->list '("NZ" "DCM" "P")))
(define input-crates (map string->list '("NVCS" "SNHJMZ" "DNJGTCM"
					 "MRWJFDT" "HFP" "JHZTC"
					 "ZLSFQRRPD" "WPFDHLSC" "ZGNFPMSD")))

(define (move crates n src target)
  (let* ((src-stack (nth crates src))
	 (elems (id (take src-stack n))) ;; replace id with reverse for part 1
	 (src-stack (drop src-stack n))
	 (target-stack (append elems (nth crates target))))
    (replace-nth (replace-nth crates src src-stack) ;; replace both new stacks
		 target target-stack)))

(list->string
 (map car
      (reduce (lambda (crates command)
		(let* ((n (inc (nth command 0)))
		       (src (nth command 1))
		       (target (nth command 2)))
		  (move crates n src target)))
	      input-crates
	      (parse "input"))))
