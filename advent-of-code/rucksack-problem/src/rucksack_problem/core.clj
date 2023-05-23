;; Advent of Code Day 03

;; Part 1
;;      Process a single rucksack
;;   1. Divide rucksack string into 2 halves
;;   2. Check for the common item (only one exists) in both halves
;;   3. Find the priority of the common item using the below relationships:
;;      Priority of a - z: 1 - 26
;;      Priority of A - Z: 27 - 52)
;;
;;      And then expand to multiple rucksacks to solve Part 1

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
  (:require [clojure.string :as string]
            [clojure.set :as set])
  (:gen-class))

(defn input-file->string-seq
  "Read file and extract strings separated by \n into a vector of strings"
  [f]
  (-> (slurp f)
      (string/split #"\n")))

(defn compartmentalize
  "Divide rucksack contents into 2 compartments
   1 string -> 2 lists of chars"
  [rucksack-contents]
  (let [half-length (/ (count rucksack-contents) 2)]
    (->> rucksack-contents
         (partition half-length))))

(defn find-common-item
  "Find the common item between 2 compartments"
  [compartment-1, compartment-2]
  (let [c1 (set compartment-1)
        c2 (set compartment-2)]
    (some #(get c2 %) c1)))
;; can also use set intersection here

(defn find-badge
  "Find the common item (badge) between a group of 3 rucksacks.
   Used in Part 2 of problem."
  [rucksack-1, rucksack-2, rucksack-3]
  (let [r1 (set rucksack-1)
        r2 (set rucksack-2)
        r3 (set rucksack-3)]
    (first (set/intersection r1 r2 r3))))

(defn calculate-priority
  "Return the priority of an item (character)"
  [item]
  (let [priority-a 1
        priority-A 27
        item-ascii (int item)
        offset (if (Character/isUpperCase item)
                 (- (int \A) priority-A)
                 (- (int \a) priority-a))]
    (- item-ascii offset)))

(defn gen-common-pair-frequencies
  "ONLY USED if we decide to lift input restrictions, and have more than one common item between compartments.
   Return hashmap with key: common item; value: number of item pairs across both compartments.
   Priority will be counted for each pair of common items."
  [compartment-1, compartment-2]
  (let [m1 (frequencies compartment-1)
        m2 (frequencies compartment-2)]
    (reduce
     (fn [m [item frequency]]
       (if (contains? m2 item)
         (->> item
              (get m2)
              (min frequency)
              (assoc m item))
         m))
     {} m1)))

(defn sum-of-priority-of-common-pairs
  "ONLY USED if we decide to lift input restrictions, and have multiple common key pairs.
   Return the sum of priorities for each pair of common items between the 2 compartments of a rucksack."
  [common-pair-frequencies]
  (->> common-pair-frequencies
       (map (fn [[item frequency]]
              (* frequency (calculate-priority item))))
       (apply +)))

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
  [rucksacks]
  (->> rucksacks
       (map common-item-priority)
       (apply +)))

(defn sum-badge-priorities
  "Sum the priorities of badges for each group of 3 rucksacks.
   Used in Part 2 of problem."
  [rucksacks]
  (->> rucksacks
       (partition 3)
       (map #(apply find-badge %))
       (map calculate-priority)
       (apply +)))

(defn -main
  "i am main"
  []
  (println "Part 1 answer: \n"
           (->> "input.txt"
                input-file->string-seq
                sum-rucksack-priorities))
  (println "Part 2 answer: \n"
           (->> "input.txt"
                input-file->string-seq
                sum-badge-priorities)))