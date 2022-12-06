(require racket/set)

(define input (string->list (port->string (open-input-file "input") #:close? #t)))
(define sample (string->list "mjqjpqmgbljsphdztnvjfqwrcgsmlb"))

(define (partition n step coll)
  "partitions a list into n large sublists with step"
  (if (> n (length coll))
      '()
      (cons (take coll n) (partition n step (drop coll step)))))
  

(define (solve contents size)
  (car (filter number?
	       (for/list ([n (build-list (length contents) (lambda (x) (+ x size)))]
			  [xs (partition size 1 contents)])
		 (if (= size (length (set->list (apply set xs))))
		     n
		     false)))))

(solve input 4) ;; part 1
(solve input 14) ;; part 2
