(defun get-file-content (file-name)
  (with-open-file (stream file-name)
    (let ((content (make-string (file-length stream))))
      (read-sequence content stream)
      content)))

(defun rps-turn-1 (opponent self)
  "returns the score earned from rps round"
  (+ self
     (case opponent
       (1 (case self
	    (1 3)
	    (2 6)
	    (3 0)))
       (2 (case self
	    (1 0)
	    (2 3)
	    (3 6)))
       (3 (case self
	    (1 6)
	    (2 0)
	    (3 3))))))

(defun rps-turn-2 (opponent result)
  "returns the score earned from rps round"
  (case result
    (1 (case opponent ;; need to loose
	 (1 3)
	 (2 1)
	 (3 2)))
    (2 (+ 3 opponent)) ;; draw
    (3 (+ 6 ;; need to win
	  (case opponent
	    (1 2)
	    (2 3)
	    (3 1))))))

(let ((content (get-file-content "input"))
      (sum 0))
  (loop when (zerop (length content)) return sum do
    (let ((opponent (case (char content 0)
		      (#\A 1)
		      (#\B 2)
		      (#\C 3)))
	  (self (case (char content 2)
		  (#\X 1)
		  (#\Y 2)
		  (#\Z 3))))
      (setq sum (+ sum (rps-turn-2 opponent self)))
      (setq content (subseq content 4)))))
