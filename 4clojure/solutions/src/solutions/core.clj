;; Solutions to 4clojure problems
;; I have inclueded multiple solutions to a few problems

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

;; short and idiomatic (destructuring doesn't work? ask udit/shivam)
#(reduce (fn [ctr val] (inc ctr)) 0 %)

;; wanted to practice using loop, not very idiomatic?
#(loop [test-seq % ctr 0]
   (if (seq test-seq)
     (recur (rest test-seq) (inc ctr))
     ctr))

;; not idiomatic, not a good way to do this
(fn count-elements
  [test-seq]
  (def ctr 0)
  (doseq [val test-seq] (def ctr (inc ctr)))
  ctr)

;; Problem 23, Reverse a Sequence
;; Write a function which reverses a sequence.
;; (= (__ [1 2 3 4 5]) [5 4 3 2 1])
;; (= (__ (sorted-set 5 7 2 7)) '(7 5 2))
;; (= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])
;; Special Restrictions : reverse

;; shortest idiomatic solution
#(reduce (fn [result val] (conj result val)) '() %)

;; using recursion
(fn reverse-seq
  [test-seq]
  (if (empty? test-seq)
    nil
    (conj (reverse-seq (butlast test-seq)) (last test-seq))))

;; practicing loops, not very idiomatic?
#(loop [test-seq % rev-seq '()]
   (if (empty? test-seq)
     rev-seq
     (recur (rest test-seq) (conj rev-seq (first test-seq)))))

;; not idiomatic, not a good way to do this
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

;; cleanest idiomatic way
#(reduce + %)

;; practicing loops again
#(loop [test-seq % sum 0]
   (if (= (count test-seq) 0)
     sum
     (recur (rest test-seq) (+ sum (first test-seq)))))

;; Problem 25, Find the odd numbers
;; Write a function which returns only the odd numbers from a sequence.
;; (= (__ #{1 2 3 4 5}) '(1 3 5))
;; (= (__ [4 2 1 6]) '(1))
;; (= (__ [2 2 4 6]) '())
;; (= (__ [1 1 1 3]) '(1 1 1 3))

;; cleanest, idiomatic way
filter #(= (mod % 2) 1)

;; practicing loops again
#(loop [remaining % result []]
    (if (empty? remaining)
      result
      (if (= (mod (first remaining) 2) 1)
        (recur (rest remaining) (conj result (first remaining)))
        (recur (rest remaining) result))))

;; Problem 26, Fibonacci Sequence
;; Write a function which returns the first X fibonacci numbers.
;; (= (__ 3) '(1 1 2))
;; (= (__ 6) '(1 1 2 3 5 8))
;; (= (__ 8) '(1 1 2 3 5 8 13 21))

;; wanted a lazy sequence solution (this is not in 4clojure format)
(defn fibo
  ([] (fibo 0 1))
  ([a b] (cons b (lazy-seq (fibo b (+ a b))))))
(take 10 (fibo))

;; lazy sequence formatted for 4clojure
#(take % ((fn fibo
            ([] (fibo 0 1))
            ([a b] (cons b (lazy-seq (fibo b (+ a b))))))))

;; using loops again
(fn fibonacci
  [n]
  (loop [ctr 0 result []]
    (if (< ctr n)
      (if (empty? result)
        (recur (inc ctr) (conj result 1))
        (recur (inc ctr) (conj result (reduce + (take-last 2 result)))))
      result)))

;; Problem 27, Palindrome Detector
;; Write a function which returns true if the given sequence is a palindrome. Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)
;; (false? (__ '(1 2 3 4 5)))
;; (true? (__ "racecar"))
;; (true? (__ [:foo :bar :foo]))
;; (true? (__ '(1 1 3 3 1 1)))
;; (false? (__ '(:a :b :c)))

#(= (seq %) (reverse %))

;; Problem 28, Flatten a Sequence
;; Write a function which flattens a sequence.
;; (= (__ '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
;; (= (__ ["a" ["b"] "c"]) '("a" "b" "c"))
;; (= (__ '((((:a))))) '(:a))
;; Special Restrictions : flatten

;; in progress

(def test-seq '((1 2) 3 [4 [5 6]]))

(defn my-flatten
  [test-seq] 
  (doseq [val test-seq] (if (sequential? val)
                          (my-flatten val)
                          (println val))))

(my-flatten test-seq)

;; Problem 29, Get the Caps
;; Write a function which takes a string and returns a new string containing only the capital letters.
;; (= (__ "HeLlO, WoRlD!") "HLOWRD")
;; (empty? (__ "nothing"))
;; (= (__ "$#A(*&987Zf") "AZ")

;; this works locally but not on 4clojure, hence using test-seq
;; not all java interop is functional in browser env
(def test-seq "HeLlO, WoRlD!")
(reduce str (filter (fn [c] (Character/isUpperCase c)) test-seq))

;; Unexpectedly, this does not pass all test cases on 4clojure
;; Vikram mentioned this is likely due to local jvm vs browser js env differences
(fn get-caps [test-seq]
  (reduce str (filter (every-pred #(>= (int %) 65) #(<= (int %) 90)) test-seq)))

;; testing the above code locally
(def test-seq1 "HeLlO, WoRlD!")
(def test-seq2 "$#A(*&987Zf")
(defn get-caps [test-seq]
 (filter (every-pred #(>= (int %) 65) #(<= (int %) 90)) test-seq))
(get-caps test-seq1)
(get-caps test-seq2)

;; Problem 30, Compress a Sequence
;; Write a function which removes consecutive duplicates from a sequence.
;; (= (apply str (__ "Leeeeeerrroyyy")) "Leroy")
;; (= (__ [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))
;; (= (__ [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

#(reduce (fn [result val]
           (if (= (last result) val)
             result
             (conj result val))) [] %)

;; Problem 31, Pack a Sequence
;; Write a function which packs consecutive duplicates into sub-lists.
;; (= (__ [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))
;; (= (__ [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))
;; (= (__ [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))