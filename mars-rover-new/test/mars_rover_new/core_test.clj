(ns mars-rover-new.core-test
  (:require [clojure.test :refer :all]
            [mars-rover-new.core :refer :all]))

(deftest get-input-test)
;; do not plan to test at this time
;; open input stream to simulate standard input, mock objects

;; need to add test for large numbers
(deftest valid-plateau-size?-test
  (testing "String should contain exactly two integers greater than 0"
    (are [expected-result actual-result] (= expected-result actual-result)
      true (valid-plateau-size? "10 10")
      true (valid-plateau-size? "500 1000")
      false (valid-plateau-size? "0 0")
      false (valid-plateau-size? "-10 10")
      false (valid-cmd-sequence? "5.2 10"))))

(deftest add-plateau-size-test
  (testing "Plateau size will be added to default map"
    (is (= {:plateau-size {:x 10
                           :y 10}
            :rover-position {:x nil
                             :y nil
                             :direction nil}
            :cmd-sequence nil}
           (add-plateau-size "10 10"
                             {:plateau-size {:x nil
                                             :y nil}
                              :rover-position {:x nil
                                               :y nil
                                               :direction nil}
                              :cmd-sequence nil})))))

(deftest valid-rover-position?-test
  (let [mars-rover-data-plateau-size {:plateau-size {:x 10
                                                     :y 10}
                                      :rover-position {:x nil
                                                       :y nil
                                                       :direction nil}
                                      :cmd-sequence nil}]
    (testing "String should contain exactly 2 integers >= 0 (and a direction character)"
      (are [expected-result actual-result] (= expected-result actual-result)
        true (valid-rover-position? "5 5 N" mars-rover-data-plateau-size)
        true (valid-rover-position? "0 0 N" mars-rover-data-plateau-size)
        false (valid-rover-position? "5 -6 N" mars-rover-data-plateau-size)))
    (testing "Both integer values, which are rover x and y coords, should be within plateau area"
      (are [expected-result actual-result] (= expected-result actual-result)
        true (valid-rover-position? "5 6 N" mars-rover-data-plateau-size)
        true (valid-rover-position? "10 10 N" mars-rover-data-plateau-size)
        false (valid-rover-position? "20 30 N" mars-rover-data-plateau-size)))
    (testing "Direction character should be contained in valid-directions set"
      (are [expected-result actual-result] (= expected-result actual-result)
        true (valid-rover-position? "0 0 N" mars-rover-data-plateau-size)
        true (valid-rover-position? "0 0 E" mars-rover-data-plateau-size)
        true (valid-rover-position? "0 0 S" mars-rover-data-plateau-size)
        true (valid-rover-position? "0 0 W" mars-rover-data-plateau-size)
        false (valid-rover-position? "0 0 D" mars-rover-data-plateau-size)
        false (valid-rover-position? "0 0 2" mars-rover-data-plateau-size)))))

(deftest add-rover-position-test
  ;; can add negative test where plateau size is nil
  (testing "Rover position will be added to map which already has plateau size"
    (is (= {:plateau-size {:x 10
                           :y 10}
            :rover-position {:x 0
                             :y 0
                             :direction "N"}
            :cmd-sequence nil}
           (add-rover-position "0 0 N"
                               {:plateau-size {:x 10
                                               :y 10}
                                :rover-position {:x nil
                                                 :y nil
                                                 :direction nil}
                                :cmd-sequence nil})))))

(deftest valid-cmd-sequence?-test
  (testing "String elements should all be contained in valid-cmds set"
    (are [x y] (= x y)
      true (valid-cmd-sequence? "LLRRMM")
      true (valid-cmd-sequence? "MMLLRR")
      true (valid-cmd-sequence? "L")
      false (valid-cmd-sequence? "llrrLL")
      false (valid-cmd-sequence? "LLZRR")
      false (valid-cmd-sequence? "LL2RR"))))

(deftest add-cmd-sequence-test
  (testing "Command sequence should be added to nested map which already contains plateau size and rover position"
    (is (= {:plateau-size {:x 10
                           :y 10}
            :rover-position {:x 0
                             :y 0
                             :direction "N"}
            :cmd-sequence "LLRRMM"}
           (add-cmd-sequence "LLRRMM"
                             {:plateau-size {:x 10
                                             :y 10}
                              :rover-position {:x 0
                                               :y 0
                                               :direction "N"}
                              :cmd-sequence nil})))))

(deftest rotate-left-test
  (testing "Rotate left will move cardinal direction anti-clockwise 90 degrees"
    (let [mars-rover-data-north {:plateau-size {:x 10
                                                :y 10}
                                 :rover-position {:x 2
                                                  :y 2
                                                  :direction "N"}
                                 :cmd-sequence "LLRRMM"}
          mars-rover-data-east {:plateau-size {:x 10
                                               :y 10}
                                :rover-position {:x 2
                                                 :y 2
                                                 :direction "E"}
                                :cmd-sequence "LLRRMM"}
          mars-rover-data-south {:plateau-size {:x 10
                                                :y 10}
                                 :rover-position {:x 2
                                                  :y 2
                                                  :direction "S"}
                                 :cmd-sequence "LLRRMM"}
          mars-rover-data-west {:plateau-size {:x 10
                                               :y 10}
                                :rover-position {:x 2
                                                 :y 2
                                                 :direction "W"}
                                :cmd-sequence "LLRRMM"}]
      (are [expected-result actual-result] (= expected-result actual-result)
        mars-rover-data-west (rotate-left mars-rover-data-north)
        mars-rover-data-north (rotate-left mars-rover-data-east)
        mars-rover-data-east (rotate-left mars-rover-data-south)
        mars-rover-data-south (rotate-left mars-rover-data-west)))))

(deftest rotate-right-test
  (testing "Rotate right will move cardinal direction anti-clockwise 90 degrees"
    (let [mars-rover-data-north {:plateau-size {:x 10
                                                :y 10}
                                 :rover-position {:x 2
                                                  :y 2
                                                  :direction "N"}
                                 :cmd-sequence "LLRRMM"}
          mars-rover-data-east {:plateau-size {:x 10
                                               :y 10}
                                :rover-position {:x 2
                                                 :y 2
                                                 :direction "E"}
                                :cmd-sequence "LLRRMM"}
          mars-rover-data-south {:plateau-size {:x 10
                                                :y 10}
                                 :rover-position {:x 2
                                                  :y 2
                                                  :direction "S"}
                                 :cmd-sequence "LLRRMM"}
          mars-rover-data-west {:plateau-size {:x 10
                                               :y 10}
                                :rover-position {:x 2
                                                 :y 2
                                                 :direction "W"}
                                :cmd-sequence "LLRRMM"}]
      (are [expected-result actual-result] (= expected-result actual-result)
        mars-rover-data-east (rotate-right mars-rover-data-north)
        mars-rover-data-south (rotate-right mars-rover-data-east)
        mars-rover-data-west (rotate-right mars-rover-data-south)
        mars-rover-data-north (rotate-right mars-rover-data-west)))))

(deftest move-test
  (testing "Move will move rover position forward one step (based) on rover direction"
    (are [expected-result actual-result] (= expected-result actual-result)
      ;; #1 Northward move
      {:plateau-size {:x 10
                      :y 10}
       :rover-position {:x 2
                        :y 3
                        :direction "N"}
       :cmd-sequence "LLRRMM"}
      (move {:plateau-size {:x 10
                            :y 10}
             :rover-position {:x 2
                              :y 2
                              :direction "N"}
             :cmd-sequence "LLRRMM"})

      ;; #2 Eastward move
      {:plateau-size {:x 10
                      :y 10}
       :rover-position {:x 3
                        :y 2
                        :direction "E"}
       :cmd-sequence "LLRRMM"}
      (move {:plateau-size {:x 10
                            :y 10}
             :rover-position {:x 2
                              :y 2
                              :direction "E"}
             :cmd-sequence "LLRRMM"})

      ;; #3 Southward move
      {:plateau-size {:x 10
                      :y 10}
       :rover-position {:x 2
                        :y 1
                        :direction "S"}
       :cmd-sequence "LLRRMM"}
      (move {:plateau-size {:x 10
                            :y 10}
             :rover-position {:x 2
                              :y 2
                              :direction "S"}
             :cmd-sequence "LLRRMM"})

      ;; #4 Westward move
      {:plateau-size {:x 10
                      :y 10}
       :rover-position {:x 1
                        :y 2
                        :direction "W"}
       :cmd-sequence "LLRRMM"}
      (move {:plateau-size {:x 10
                            :y 10}
             :rover-position {:x 2
                              :y 2
                              :direction "W"}
             :cmd-sequence "LLRRMM"}))))

(deftest process-user-input-test)
;; not testing user input functions at this time

(deftest process-cmds-test
  (testing "Rover will be moved from initial position -> final position based on command sequence"
    (is (= {:plateau-size {:x 10
                           :y 10}
            :rover-position {:x 2
                             :y 4
                             :direction "E"}
            :cmd-sequence "MMRMMLMMR"}
           (process-cmds {:plateau-size {:x 10
                                         :y 10}
                          :rover-position {:x 0
                                           :y 0
                                           :direction "N"}
                          :cmd-sequence "MMRMMLMMR"})))))

(deftest display-result-test)
;; not testing println display functions at this time