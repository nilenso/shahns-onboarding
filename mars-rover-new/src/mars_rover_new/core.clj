;; Mars Rover Exercise

;; todo: input validation, check invalid moves

(ns mars-rover-new.core
  (:require [clojure.string :as string])
  (:gen-class))

(declare add-plateau-size, add-rover-position, add-cmd-sequence)

(def info-map {:plateau-size {:x nil
                              :y nil}
               :rover-position {:x nil
                                :y nil
                                :direction nil}
               :cmd-sequence nil})

(defn get-input
  "Prompt (if provided) and read one line of input stream. Trim and convert to upper-case."
  ([] (get-input ""))
  ([prompt]
   (when (not-empty prompt)
     (println prompt))
   (let [input (string/trim (read-line))]
     (string/upper-case input))))

(defn user-input
  "Reads 3 lines of input from user and calls 3 functions which add this data to info-map"
  [] 
  (let [plateau-size (get-input "Enter plateau size:")
        rover-position (get-input "Enter initial rover position:")
        cmd-sequence (get-input "Enter command sequence:")]
    (->> info-map
         (add-plateau-size plateau-size)
         (add-rover-position rover-position)
         (add-cmd-sequence cmd-sequence))))

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