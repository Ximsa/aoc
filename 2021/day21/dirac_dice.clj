(defn roll_dice [dice]
  [(+ 3 dice) (+ (* 3 dice) 3)])

(defn update [dice player]
  (def result (roll_dice dice))
  [(first result) (conj player (inc(mod (dec (+ (last result) (last player))) 10)))])

(defn game [player1 player2 dice]
  (def result (update dice player1))
  (def score_player1 (apply + (rest player1)))
  (def score_player2 (apply + (rest player2)))
;;  (println (str score_player1 "\t" score_player2))
  (if (>= score_player2 1000)
    (* score_player1 (dec dice))
    (game player2 (last result) (first result))))

(game [6] [10] 1)
