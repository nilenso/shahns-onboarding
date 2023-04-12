;; Mars Rover Exercise

;; todo: input validation, check invalid moves

(ns mars-rover-new.core
  (:require [clojure.string :as string])
  (:gen-class))

(declare add-plateau-size, add-rover-position, add-cmd-sequence)

(def valid-directions #{"N" "E" "S" "W"})
(def valid-cmds #{"L" "R" "M"})

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

(defn split-string
  "Split string on white-space and remove empty elements"
  [s]
  (filter #(not-empty %) (string/split s #" ")))

(defn string-only-digits?
  "Returns true if string contains only integers"
  [s]
  (every? #(Character/isDigit %) s))

(defn string-seq-only-digits?
  "Returns true if all strings in sequence contain only integers"
  [str-seq]
  (every? #(= true %) (map string-only-digits? str-seq)))

(defn str->int
  "Convert string to integer"
  [s]
  (Integer/parseInt s))

(defn in-range?
  "Check if val is within bounds (inclusive)"
  [val lower-bound upper-bound]
  (if (and (>= val lower-bound) (<= val upper-bound))
    true false))

(defn verify-plateau-size
  "Check that input contains exactly two integers greater than 0"
  [plateau-size]
  (let [str-seq (split-string plateau-size)
        [x y] str-seq
        type-test (string-seq-only-digits? str-seq)
        count-test (= 2 (count str-seq))]
    (if (and type-test count-test
             (> (str->int x) 0)
             (> (str->int y) 0))
      true false)))

(defn verify-rover-position
  "Verify that input contains exactly 2 integers and 1 direction character.
   Ensure rover position is within plateau size."
  [rover-position, info-map]
  (let [str-seq (split-string rover-position)
        [x y direction] str-seq
        max-x (get-in info-map [:plateau-size :x])
        max-y (get-in info-map [:plateau-size :y])
        count-test (= (count str-seq) 3)
        type-test (and (string-only-digits? x) (string-only-digits? y) (contains? valid-directions direction))]
    (if (and count-test type-test
             (in-range? (str->int x) 0 max-x)
             (in-range? (str->int y) 0 max-y))
      true false)))

(defn verify-cmd-sequence
  [cmd-sequence]
  (let [valid-cmd-test (every? #(contains? valid-cmds %) (map #(str (identity %)) cmd-sequence))]
    (if valid-cmd-test true false)))

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