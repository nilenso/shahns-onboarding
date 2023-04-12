;; Mars Rover Exercise

;; todo: input validation, check invalid moves

(ns mars-rover-new.core
  (:require [clojure.string :as string])
  (:gen-class))

(def valid-directions #{"N" "E" "S" "W"})
(def valid-cmds #{"L" "R" "M"})

(def default-mars-rover-data {:plateau-size {:x nil
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

(defn split-string
  "Split string on white-space and remove empty elements"
  [s]
  (filter #(not-empty %) (string/split s #" ")))

(defn str->int
  "Convert string to integer. If invalid string, return nil"
  [s]
  (try (Integer/parseInt s)
       (catch Exception e nil)))

(defn in-range?
  "Check if val is within bounds (inclusive)"
  [val lower-bound upper-bound]
  (if (and (>= val lower-bound) (<= val upper-bound))
    true false))

(defn valid-plateau-size?
  "Check that input contains exactly two integers greater than 0"
  [plateau-size]
  (let [str-seq (split-string plateau-size)
        count-test (= 2 (count str-seq))
        [x y] (map str->int str-seq)]
    (and count-test x y (> x 0) (> y 0))))

(defn valid-rover-position?
  "Verify that input contains exactly 2 integers and 1 direction character.
   Ensure rover position is within plateau size."
  [rover-position, mars-rover-data]
  (let [str-seq (split-string rover-position)
        count-test (= (count str-seq) 3)
        [x y] (map str->int (butlast str-seq))
        direction (get valid-directions (last str-seq))
        max-x (get-in mars-rover-data [:plateau-size :x])
        max-y (get-in mars-rover-data [:plateau-size :y])]
    (and count-test x y direction (in-range? x 0 max-x) (in-range? y 0 max-y))))

(defn valid-cmd-sequence?
  "Returns true if command sequence contains valid commands"
  [cmd-sequence]
  (every? #(contains? valid-cmds %) (map #(str (identity %)) cmd-sequence)))

(defn add-plateau-size
  "Add plateau-size data to info-map"
  [plateau-size, info-map]
  (let [[x y] (map #(Integer/parseInt %) (string/split plateau-size #" "))]
    (assoc mars-rover-data :plateau-size {:x x
                                   :y y})))

(defn add-rover-position
  "Add rover-position data to mars-rover-data"
  [rover-position, mars-rover-data]
  (let [[x y d] (string/split rover-position #" ")]
    (assoc mars-rover-data :rover-position {:x (Integer/parseInt x)
                                            :y (Integer/parseInt y)
                                            :direction d})))

(defn add-cmd-sequence
  "Add cmd-sequence data to mars-rover-data"
  [cmd-sequence, mars-rover-data]
  (assoc mars-rover-data :cmd-sequence cmd-sequence))

(defn process-user-input
  []
  (let [plateau-size (get-input "Enter plateau size:")
        rover-position (get-input "Enter initial rover position:")
        cmd-sequence (get-input "Enter command sequence:")]
    (->> default-mars-rover-data
         (add-plateau-size plateau-size)
         (add-rover-position rover-position)
         (add-cmd-sequence cmd-sequence))))

(defn -main
  "Program entry point"
  [& args]
  (println "Welcome to Mars!")
  (prn (process-user-input)))