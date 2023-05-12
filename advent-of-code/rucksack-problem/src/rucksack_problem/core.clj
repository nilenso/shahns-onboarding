;; Advent of Code Day 3

;; Part 1
;;      First version: Process a single rucksack
;;   1. Divide rucksack string into 2 halves
;;   2. Check for the common item (only one exists) in both halves
;;   3. Find the priority of the common item using the below relationships:
;;      Priority of a - z: 1 - 26
;;      Priority of A - Z: 27 - 52)
;;
;;      And then expand to multiple rucksacks.

;; Lifting some input restrictions as another exercise
;; Lift restriction of a single common item. Code generic solution where priorities are counted
;; for each pair of common items between the 2 compartments.
;; 1. create hashmap for compartment 1 and 2 with key: item, value: frequency of items
;; 2. find pairs of common keys between both hashmaps
;; 3. minimum value of each key pair is the priority multiple
;; 4. sum of (priority * multiple) for each key pair

;; Part 2
;; For 1 group of 3 rucksacks
;; 1. find common item in each group of 3 rucksacks.
;; 2. find priority of common item
;; Repeat the above for each group of 3 rucksacks, and sum the priorities of common items.

(ns rucksack-problem.core
  (:require [clojure.string :as string])
  (:gen-class))

;; For simplicity, we define one test input as a list of strings
(def input-list (list "vJrwpWtwJgWrhcsFMMfFFhFp"
                      "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
                      "PmmdzqPrVvPwwTWBwg"
                      "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
                      "ttgJtRGJQctTZtZT"
                      "CrZsJsPPZsGzwwsLwLmpwMDw"))

(defn input-file->string-seq
  "Read file and extract strings separated by \n into a vector of strings"
  [f]
  (string/split (slurp f) #"\n"))

;; (input-file->string-list "input.txt")

(defn charlist->string
  "Convert a list of characters to a string"
  [charlist]
  (apply str charlist))

(defn compartmentalize
  "Divide rucksack contents (one string) into 2 compartments (two strings)"
  [rucksack-contents]
  (map charlist->string
       (partition (/ (count rucksack-contents) 2) rucksack-contents)))

(defn find-common-item
  "Find the common item between 2 compartments"
  [compartment-1, compartment-2]
  (let [c1 (set compartment-1)
        c2 (set compartment-2)]
    (some #(get c2 %) c1)))

(defn calculate-priority
  "Return the priority of an item (character)"
  [item]
  (let [priority-a 1
        priority-A 27
        offset (if (Character/isUpperCase item)
                 (- (int \A) priority-A)
                 (- (int \a) priority-a))]
    (- (int item) offset)))

(defn gen-common-keys-map
  "Find pairs of common keys between two hashmaps.
   Return hashmap with key: common key; value: min value of key across both maps.
   Used if we decide to lift input restrictions, and have multiple common key pairs."
  [m1 m2]
  (reduce
   (fn [m [item freq]]
     (if (contains? m2 item)
       (assoc m item (min (get m1 item) (get m2 item)))
       m))
   {} m1))

(gen-common-keys-map {\P 2 \a 1 \c 3} {\B 4 \P 1 \c 2})

(defn sum-priorities-of-common-item-pairs
  "Return the sum of priorities for each pair of common items between the 2 compartments of a rucksack
   Used if we decide to lift input restrictions, and have multiple common key pairs."
  [compartment-1, compartment-2]
  (let [m1 (frequencies compartment-1)
        m2 (frequencies compartment-2)
        common-key-map (gen-common-keys-map m1 m2)]
    (->> common-key-map
         (map (fn [[item freq]]
                (* freq (calculate-priority item))))
         (apply +))))

;; (common-item-priority "PmmdzqPrVvPwwTWBwg")
(apply sum-priorities-of-common-item-pairs (compartmentalize "PPmmdzqPrVvPPPwwwTWBwg"))

(defn common-item-priority
  "Return the priority of the common item between the 2 compartments of a rucksack"
  [rucksack-contents]
  (->> rucksack-contents
       compartmentalize
       (apply find-common-item)
       calculate-priority))

;; look into pros/cons of apply vs reduce (memory, readability)
(defn sum-rucksack-priorities
  "Return the total sum of common-item-priority for each rucksack in the supplied input list"
  [input]
  (->> input
       (map common-item-priority)
       (apply +)))

(sum-rucksack-priorities input-list)
(sum-rucksack-priorities (input-file->string-seq "input.txt"))

(defn -main
  "i am main"
  []
  (println "Sum of common element priorities across all rucksacks: \n"
           (sum-rucksack-priorities input-list)))


