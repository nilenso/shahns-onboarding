;; Solutions to 4clojure problems

(ns solutions.core
  (:gen-class))

;; Problem 1, Nothing but the Truth
;; (= __ true)
true

(= 2 2)

;; Problem 2, Simple Math
;; (= (- 10 (* 2 3)) __)
4

;; Problem 3, Strings
;; (= __ (.toUpperCase "hello world"))
"HELLO WORLD"

;; Problem 4, Lists
;; (= (list __) '(:a :b :c))
:a :b :c

;; Problem 5, conj on lists
;; (= __ (conj '(2 3 4) 1))
;; (= __ (conj '(3 4) 2 1))
(list 1 2 3 4)
'(1 2 3 4)

;; Problem 6, Vectors
;; (= [__] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))
:a :b :c

;; Problem 7, conj on vectors
;;(= __ (conj [1 2 3] 4))
;;(= __ (conj [1 2] 3 4))
(vec '(1 2 3 4))
(vector 1 2 3 4)

;; Problem 8, Sets
;; (= __ (set '(:a :a :b :c :c :c :c :d :d)))
;; (= __ (clojure.set/union #{:a :b :c} #{:b :c :d}))
#{:a :b :c :d}
(hash-set :a :b :c :d)

;; Problem 9, conj on sets
;; (= #{1 2 3 4} (conj #{1 4 3} __))
2

;; Problem 10, Maps
;; (= __ ((hash-map :a 10, :b 20, :c 30) :b))
;; (= __ (:b {:a 10, :b 20, :c 30}))
(get {:a 10 :b 20 :c 30} :b)
20