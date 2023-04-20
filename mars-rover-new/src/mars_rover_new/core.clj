;; Mars Rover Exercise

(ns mars-rover-new.core
  (:require [clojure.string :as string]
            [clojure.pprint :as pp])
  (:gen-class))

(def valid-directions #{"N" "E" "S" "W"})
(def valid-cmds #{"L" "R" "M"})

;; transforms defines the result of left, right and move transforms
;; key -> current location; value -> location after applying transform
(def transforms {:left {"N" "W"
                        "E" "N"
                        "S" "E"
                        "W" "S"}
                 :right {"N" "E"
                         "E" "S"
                         "S" "W"
                         "W" "N"}
                 :move {"N" {:ord :y
                             :action inc}
                        "E" {:ord :x
                             :action inc}
                        "S" {:ord :y
                             :action dec}
                        "W" {:ord :x
                             :action dec}}})

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

(defn truthy
  "Returns false if val is nil or false otherwise true"
  [val]
  (if val true false))

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
    (truthy (and count-test x y (> x 0) (> y 0)))))

(defn valid-rover-position?
  "Verify that input contains exactly 2 integers and 1 direction character.
   Ensure rover position is within plateau size."
  [rover-position, mars-rover-data]
  (let [str-seq (split-string rover-position)
        count-test (= (count str-seq) 3)
        [x y] (map str->int (butlast str-seq))
        direction (get valid-directions (last str-seq))
        {max-x :x max-y :y} (get mars-rover-data :plateau-size)]
    (truthy (and count-test x y direction (in-range? x 0 max-x) (in-range? y 0 max-y)))))

(defn valid-cmd-sequence?
  "Returns true if command sequence contains valid commands"
  [cmd-sequence]
  (every? #(contains? valid-cmds (str %)) (seq cmd-sequence)))

(defn add-plateau-size
  "Verify and add plateau-size data to mars-rover-data"
  [plateau-size, mars-rover-data]
  {:pre [(valid-plateau-size? plateau-size)]}
  (let [[x y] (map #(Integer/parseInt %) (string/split plateau-size #" "))]
    (assoc mars-rover-data :plateau-size {:x x
                                          :y y})))

(defn add-rover-position
  "Verify and add rover-position data to mars-rover-data"
  [rover-position, mars-rover-data]
  {:pre [(valid-rover-position? rover-position mars-rover-data)]}
  (let [[x y d] (string/split rover-position #" ")]
    (assoc mars-rover-data :rover-position {:x (Integer/parseInt x)
                                            :y (Integer/parseInt y)
                                            :direction d})))

(defn add-cmd-sequence
  "Verify and add cmd-sequence data to mars-rover-data"
  [cmd-sequence, mars-rover-data]
  {:pre [(valid-cmd-sequence? cmd-sequence)]}
  (assoc mars-rover-data :cmd-sequence cmd-sequence))

(defn rotate-left
  "Rotate rover counter-clockwise 90 degrees"
  [mars-rover-data]
  (assoc-in mars-rover-data [:rover-position :direction] (get-in transforms [:left (get-in mars-rover-data [:rover-position :direction])])))

(defn rotate-right
  "Rotate rover clockwise 90 degrees"
  [mars-rover-data]
  (assoc-in mars-rover-data [:rover-position :direction] (get-in transforms [:right (get-in mars-rover-data [:rover-position :direction])])))

(defn move
  "Move rover forward one unit"
  [mars-rover-data]
  (let [direction (get-in mars-rover-data [:rover-position :direction])
        {key :ord op :action} (get-in transforms [:move direction])]
    (assoc-in mars-rover-data [:rover-position key] (op (get-in mars-rover-data [:rover-position key])))))

;; mapping of L, R and M to their respective core functions
(def cmd->fn {"L" mars-rover-new.core/rotate-left
              "R" mars-rover-new.core/rotate-right
              "M" mars-rover-new.core/move})

(defn process-user-input
  "Reads 3 lines of input from user and calls functions to input data to mars-rover-data"
  []
  (let [plateau-size (get-input "Enter plateau size:")
        rover-position (get-input "Enter initial rover position:")
        cmd-sequence (get-input "Enter command sequence:")]
    (try
      (->> default-mars-rover-data
           (add-plateau-size plateau-size)
           (add-rover-position rover-position)
           (add-cmd-sequence cmd-sequence))
      (catch AssertionError e
        (let [msg (.getMessage e)]
          (when (.contains msg "plateau-size")
            (println "Plateau size error, please try again."))
          (when (.contains msg "rover-position")
            (println "Rover initial position error, please try again."))
          (when (.contains msg "cmd-sequence")
            (println "Command sequence error, please try again.")))
        (process-user-input)))))

;; process-cmds uses cmd->fn to get the core functions corresponding to L, R and M commands
(defn process-cmds
  "Reduces over a list of functions and builds up the rover's final position"
  [mars-rover-data]
  (reduce (fn [new-mars-rover-data cmd] ((get cmd->fn cmd) new-mars-rover-data))
          mars-rover-data
          (map str (get mars-rover-data :cmd-sequence))))

(defn validate-rover-position
  "Check that rover position is valid: not negative and within plateau.
   Different from valid-rover-position? which checks the input string."
  [mars-rover-data]
  (let [{x :x y :y} (get mars-rover-data :rover-position)
        {max-x :x max-y :y} (get mars-rover-data :plateau-size)]
    (and (in-range? x 0 max-x) (in-range? y 0 max-y))))

(defn display-result
  "Display final rover position and check if position is within plateau area"
  [mars-rover-data]
  (let [{x :x y :y d :direction} (get mars-rover-data :rover-position)]
    (if (validate-rover-position mars-rover-data)
      (println "Final rover position:")
      (println "Final rover position outside plateau area, you destroyed the rover:"))
    (println x y d)))

(defn -main
  "Program entry point"
  [& args]
  (println "Welcome to Mars!")
  (->> (process-user-input)
       (process-cmds)
       (display-result)))