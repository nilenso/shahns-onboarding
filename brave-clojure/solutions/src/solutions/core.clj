;;;; Solutions to Brave Clojure exercises

(ns solutions.core
  (:gen-class))

;;; Brave Clojure Chapter 3

;; 1. Use the str, vector, list, hash-map, and hash-set functions.

(str "Hello, " "I am learning " "Clojure.")

(vector 1 2 3 4)

(list 1 2 3 4)

(hash-set 2 2 2 2 2 2 2 2 7 7 7)

(conj (hash-set 1 1 1 1 2 2) 3)

(hash-map :a "Nil" :b "en" :c "so")

(def name_sections {:a "Nil", :b "en", :c "so"})
(str (get name_sections :a) (get name_sections :b) (get name_sections :c))
(str (name_sections :a) (name_sections :b) (name_sections :c))
(str (:a name_sections) (:b name_sections) (:c name_sections))

;; 2. Write a function that takes a number and adds 100 to it.
(defn add-hundred
  "Takes a number and adds 100 to it"
  [n]
  (+ n 100))

(add-hundred 100)

;; 3. Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction:
;; (def dec9 (dec-maker 9))
;; (dec9 10)
;; ; => 1

(defn dec-maker
  "Custom decrementor"
  [dec-by]
  #(- % dec-by))