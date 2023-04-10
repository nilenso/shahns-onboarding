;; Mars Rover Exercise

(ns mars-rover-new.core
  (:require [clojure.string :as string])
  (:gen-class))

;; todo: input validation, check invalid moves

(declare add-plateau-size, add-rover-position, add-cmd-sequence)

(def info-map {:plateau-size {:x nil
                              :y nil}
               :rover-position {:x nil
                                :y nil
                                :direction nil}
               :cmd-sequence nil})

(defn get-input
  "Read one line of input stream. Trim and convert to upper-case."
  []
  (let [input (string/trim (read-line))]
    (string/upper-case input)))

(defn user-input
  "Reads 3 lines of input from user and calls 3 functions which add this data to info-map"
  []
  (println "Please enter plateau-size, initial rover position and command sequence on 3 separate lines:")
  (let [plateau-size (get-input)
        rover-position (get-input)
        cmd-sequence (get-input)]
    (add-cmd-sequence cmd-sequence (add-rover-position rover-position (add-plateau-size plateau-size info-map)))))

(defn add-plateau-size
  "Add plateau-size data to info-map"
  [plateau-size, info-map]
  (let [[x y] (map #(Integer/parseInt %) (string/split plateau-size #" "))]
    (assoc-in info-map [:plateau-size] {:x x
                                        :y y})))

(defn add-rover-position
  "Add rover-position data to info-map"
  [rover-position, info-map]
  (let [[x y d] (string/split rover-position #" ")]
    (assoc-in info-map [:rover-position] {:x (Integer/parseInt x)
                                 :y (Integer/parseInt y)
                                 :direction d})))

(defn add-cmd-sequence
  "Add cmd-sequence data to info-map"
  [cmd-sequence, info-map]
  (assoc-in info-map [:cmd-sequence] cmd-sequence))

(defn -main
  "Program entry point"
  [& args]
  (println "Welcome to Mars!")
  (prn (user-input)))

;; repl testing
;; (add-plateau-size "10 100" info-map)
;; (add-initial-position "0 0 N" info-map)
;; (add-cmd-sequence "LLRRMM" info-map)

;; (add-cmd-sequence "LLRRMM" (add-initial-position "0 0 N" (add-plateau-size "10 10" info-map)))
