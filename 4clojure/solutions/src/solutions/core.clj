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

;; Problem 11, conj on maps
;; When operating on a map, the conj function returns a new map with one or more key-value pairs "added".
;; (= {:a 1, :b 2, :c 3} (conj {:a 1} __ [:c 3]))

[:b 2]

;; Problem 12, Sequences
;; All Clojure collections support sequencing. You can operate on sequences with functions like first, second, and last.
;; (= __ (first '(3 2 1)))
;; (= __ (second [2 3 4]))
;; (= __ (last (list 1 2 3)))

(nth '(4 3 2 1) 1)
(second [4 3 2 1])
(last [1 2 3])

;; Problem 13, rest
;; The rest function will return all the items of a sequence except the first.
;; (= __ (rest [10 20 30 40]))

[20 30 40]
'(20 30 40)

;; Problem 14, Functions
;; Clojure has many different ways to create functions.
;; (= __ ((fn add-five [x] (+ x 5)) 3))
;; (= __ ((fn [x] (+ x 5)) 3))
;; (= __ (#(+ % 5) 3))
;; (= __ ((partial + 5) 3))

8
((partial * 2) 2 2)

;; Problem 15, Double Down
;; Write a function which doubles a number.
;; (= (__ 2) 4)
;; (= (__ 3) 6)
;; (= (__ 11) 22)
;; (= (__ 7) 14)

#(+ % %)
(fn [n] (+ n n))
(fn double-num [n] (+ n n))
(partial * 2)

;; Problem 16, Hello World
;; Write a function which returns a personalized greeting.
;; (= (__ "Dave") "Hello, Dave!")
;; (= (__ "Jenn") "Hello, Jenn!")
;; (= (__ "Rhea") "Hello, Rhea!")

#(str "Hello, " % "!")
(fn [n] (str "Hello, " n "!"))

;; Problem 17, map
;; The map function takes two arguments: a function (f) and a sequence (s). Map returns a new sequence consisting of the result of applying f to each item of s. Do not confuse the map function with the map data structure.
;; (= __ (map #(+ % 5) '(1 2 3)))

'(6 7 8)
(map #(- 10 %) '(4 3 2))

;; Problem 18, filter
;; The filter function takes two arguments: a predicate function (f) and a sequence (s). Filter returns a new sequence consisting of all the items of s for which (f item) returns true.
;; (= __ (filter #(> % 5) '(3 4 5 6 7)))

'(6 7)
(map #(+ 5 %) '(1 2))
(filter #(< % 10) '(100, 23, 11, 6, 7))

;; Problem 19, Last Element
;; Write a function which returns the last element in a sequence.
;; (= (__ [1 2 3 4 5]) 5)
;; (= (__ '(5 4 3)) 3)
;; (= (__ ["b" "c" "d"]) "d")
;; Special Restrictions : last

#(first (reverse %))

;; Problem 20, Penultimate Element
;; Write a function which returns the second to last element from a sequence.
;; (= (__ (list 1 2 3 4 5)) 4)
;; (= (__ ["a" "b" "c"]) "b")
;; (= (__ [[1 2] [3 4]]) [1 2])

#(second (reverse %))

;; Problem 21, Nth Element
;; Write a function which returns the Nth element from a sequence.
;; (= (__ '(4 5 6 7) 2) 6)
;; (= (__ [:a :b :c] 0) :a)
;; (= (__ [1 2 3 4] 1) 2)
;; (= (__ '([1 2] [3 4] [5 6]) 2) [5 6])
;; Special Restrictions : nth

;; (+ %2 1) takes care of zero indexation
#(last (take (+ %2 1) %1))

;; Problem 22, Count a Sequence
;; Write a function which returns the total number of elements in a sequence.
;; (= (__ '(1 2 3 3 1)) 5)
;; (= (__ "Hello World") 11)
;; (= (__ [[1 2] [3 4] [5 6]]) 3)
;; (= (__ '(13)) 1)
;; (= (__ '(:a :b :c)) 3)
;; Special Restrictions : count

(fn count-elements
  [test-seq]
  (def ctr 0)
  (doseq [val test-seq] (def ctr (inc ctr)))
  ctr)

;; wanted to use loop, got help from the solutions
#(loop [test-seq % ctr 0]
   (if (seq test-seq)
     (recur (rest test-seq) (inc ctr))
     ctr))

;; Problem 23, Reverse a Sequence
;; Write a function which reverses a sequence.
;; (= (__ [1 2 3 4 5]) [5 4 3 2 1])
;; (= (__ (sorted-set 5 7 2 7)) '(7 5 2))
;; (= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])
;; Special Restrictions : reverse

(fn rev-seq
  [test-seq]
  (def result '())
  (doseq [val test-seq] (def result (conj result val)))
  result)

;; Problem 24, Sum It All Up
;; Write a function which returns the sum of a sequence of numbers.
;; (= (__ [1 2 3]) 6)
;; (= (__ (list 0 -2 5 5)) 8)
;; (= (__ #{4 2 1}) 7)
;; (= (__ '(0 0 -1)) -1)
;; (= (__ '(1 10 3)) 14)

#(reduce + %)

#(loop [test-seq % sum 0]
   (if (= (count test-seq) 0)
     sum
     (recur (rest test-seq) (+ sum (first test-seq)))))