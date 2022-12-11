(def monkeys (atom
             [{:inspections 0
               :items [79 98]
               :operation #(* %1 19)
               :test #(if (zero? (mod %1 23)) 2 3)}
              {:inspections 0
               :items [54 65 75 74]
               :operation #(+ %1 6)
               :test #(if (zero? (mod %1 19)) 2 0)}
              {:inspections 0
               :items [79 60 97]
               :operation (fn [x] (* x x))
               :test #(if (zero? (mod %1 13)) 1 3)}
              {:inspections 0
               :items [74]
               :operation #(+ %1 3)
               :test #(if (zero? (mod %1 17)) 0 1)}]))

(def monkeys (atom
             [{:inspections 0
               :items [54 89 94]
               :operation #(* %1 7)
               :test #(if (zero? (mod %1 17)) 5 3)}
              {:inspections 0
               :items [66 71]
               :operation #(+ %1 4)
               :test #(if (zero? (mod %1 3)) 0 3)}
              {:inspections 0
               :items [76 55 80 55 55 96 78]
               :operation #(+ %1 2)
               :test #(if (zero? (mod %1 5)) 7 4)}
              {:inspections 0
               :items [93 69 76 66 89 54 59 94]
               :operation #(+ %1 7)
               :test #(if (zero? (mod %1 7)) 5 2)}
              {:inspections 0
               :items [80 54 58 75 99]
               :operation #(* %1 17)
               :test #(if (zero? (mod %1 11)) 1 6)}
              {:inspections 0
               :items [69 70 85 83]
               :operation #(+ %1 8)
               :test #(if (zero? (mod %1 19)) 2 7)}
              {:inspections 0
               :items [89]
               :operation #(+ %1 6)
               :test #(if (zero? (mod %1 2)) 0 1)}
              {:inspections 0
               :items [62 80 58 57 93 56]
               :operation (fn [n] (* n n))
               :test #(if (zero? (mod %1 13)) 6 4)}]))

(doseq [iterations (range 10000)]
  (doseq [monkey-index (range (count @monkeys))]
    (while (> (count (:items (nth @monkeys monkey-index))) 0)
      (swap! monkeys (fn [old]
                       (let [item (first (:items (nth old monkey-index)))
                             operation (:operation (nth old monkey-index))
                             test (:test (nth old monkey-index))
                             ;;new-value (quot (operation item) 3) ;; part 1
                             new-value (mod (operation item) 9699690) ;; part 2
                             target-index (test new-value)]
                         (-> old
                             (update-in [monkey-index :items] rest) ;; remove first element
                             (update-in [monkey-index :inspections] inc) ;; increment inspection count
                             (update-in [target-index :items] #(vec (conj %1 new-value))))))))));; throw item to target monkey

(->> @monkeys
     (map :inspections)
     (sort >)
     (take 2)
     (apply *))
