(ns rucksack-problem.core-test
  (:require [clojure.test :refer :all]
            [rucksack-problem.core :refer :all])
  (:gen-class))

(deftest compartmentalize-test
  (testing "Divide rucksack contents into 2 halves"
    (let [rucksack "vJrwpWtwJgWrhcsFMMfFFhFp"
          first-half '(\v \J \r \w \p \W \t \w \J \g \W \r)
          second-half '(\h \c \s \F \M \M \f \F \F \h \F \p)]
      (is (= (list first-half second-half)
             (compartmentalize rucksack))))))

(deftest find-common-item-test
  (testing "Return the common element between the 2 compartments"
    (are [expected-result actual-result] (= expected-result actual-result)
      \p (apply find-common-item
                (compartmentalize "vJrwpWtwJgWrhcsFMMfFFhFp"))
      \L (apply find-common-item
                (compartmentalize "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"))
      \P (apply find-common-item
                (compartmentalize "PmmdzqPrVvPwwTWBwg")))))

(deftest find-badge-test
  (testing "Return the common item (badge) between a group of 3 rucksacks"
    (let [rucksack-1 "vJrwpWtwJgWrhcsFMMfFFhFp"
          rucksack-2 "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
          rucksack-3 "PmmdzqPrVvPwwTWBwg"]
      (is (= \r
             (find-badge rucksack-1 rucksack-2 rucksack-3))))))

(deftest calculate-priority-test
  (testing "Return the priority of an item"
    (are [expected actual] (= expected actual)
      1 (calculate-priority \a)
      26 (calculate-priority \z)
      27 (calculate-priority \A)
      52 (calculate-priority \Z))))

(deftest common-item-priority-test
  (testing "Return the priority of the common item between the 
            2 compartments of a rucksack"
    (is (= 16 (common-item-priority "vJrwpWtwJgWrhcsFMMfFFhFp")))
    (is (= 38 (common-item-priority "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")))))

(deftest sum-rucksack-priorities-test
  (testing "Return the sum of common-item-priority for each 
            rucksack in the supplied input list"
    (let [test-input '("vJrwpWtwJgWrhcsFMMfFFhFp"
                       "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
                       "PmmdzqPrVvPwwTWBwg"
                       "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
                       "ttgJtRGJQctTZtZT"
                       "CrZsJsPPZsGzwwsLwLmpwMDw")]
      (is (= 157 (sum-rucksack-priorities test-input))))))

(deftest sum-badge-priorities-test
  (testing "Return sum of priorities of badges for each group of 3 rucksacks"
    (let [test-input (list "vJrwpWtwJgWrhcsFMMfFFhFp"
                           "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
                           "PmmdzqPrVvPwwTWBwg"
                           "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
                           "ttgJtRGJQctTZtZT"
                           "CrZsJsPPZsGzwwsLwLmpwMDw")]
      (is (= 70 (sum-badge-priorities test-input))))))