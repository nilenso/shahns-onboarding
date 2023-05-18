(ns rucksack-problem.core-test
  (:require [clojure.test :refer :all]
            [rucksack-problem.core :refer :all]))

;;      First version: Process a single rucksack
;;   1. Divide rucksack string into 2 halves
;;   2. Check for the common item (only one exists) in both halves
;;   3. Find the priority of the common item using the below relationships:
;;      Priority of a - z: 1 - 26
;;      Priority of A - Z: 27 - 52)
;;
;;      And then expand to multiple rucksacks

(deftest sum-rucksack-priorities-test
  (testing "Return the sum of common-item-priority for each rucksack in the supplied input list"
    (is (= 157 (sum-rucksack-priorities input-list)))))

(deftest common-item-priority-test
  (testing "Return the priority of the common item between the 2 compartments of a rucksack"
    (is (= 16 (common-item-priority "vJrwpWtwJgWrhcsFMMfFFhFp")))
    (is (= 38 (common-item-priority "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")))))

(deftest compartmentalize-test
  (testing "Divide rucksack contents into 2 halves"
    (is (= '("vJrwpWtwJgWr" "hcsFMMfFFhFp")
           (compartmentalize "vJrwpWtwJgWrhcsFMMfFFhFp")))))

(deftest common-item-test
  (testing "Return the common element between the 2 compartments"
    (are [expected-result actual-result] (= expected-result actual-result)
      \p (apply find-common-item (compartmentalize "vJrwpWtwJgWrhcsFMMfFFhFp"))
      \L (apply find-common-item (compartmentalize "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"))
      \P (apply find-common-item (compartmentalize "PmmdzqPrVvPwwTWBwg")))))

(deftest priority-test
  (testing "Return the priority of an item"
    (are [expected actual] (= expected actual)
      1 (calculate-priority \a)
      26 (calculate-priority \z)
      27 (calculate-priority \A)
      52 (priority \Z))))

;; automate priority-test case generation?
;; (for [i (range 97 123)
;;       :let [j (- i 96)]]
;;   (prn (char i) j)) 