;; Mars Rover Exercise

(ns mars-rover-new.core
  (:require [clojure.string :as string]
            [clojure.pprint :as pp])
  (:gen-class))

;; (declare mars-rover-new.core/rotate-left, mars-rover-new.core/rotate-right, mars-rover-new.core/move)
(declare rotate-left, rotate-right, move)

(def valid-directions #{"N" "E" "S" "W"})
(def valid-cmds #{"L" "R" "M"})

;; mapping of L, R and M to their respective core functions
(def fn-map {"L" mars-rover-new.core/rotate-left
             "R" mars-rover-new.core/rotate-right
             "M" mars-rover-new.core/move})

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
                 :move {"N" {:coord :y
                             :action inc}
                        "E" {:coord :x
                             :action inc}
                        "S" {:coord :y
                             :action dec}
                        "W" {:coord :x
                             :action dec}}})

(def default-mars-rover-data {:plateau-size {:x nil
                                             :y nil}
                              :rover {:x nil
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
    (assoc mars-rover-data :rover {:x (Integer/parseInt x)
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
  (assoc-in mars-rover-data [:rover :direction] (get-in transforms [:left (get-in mars-rover-data [:rover :direction])])))

(defn rotate-right
  "Rotate rover clockwise 90 degrees"
  [mars-rover-data]
  (assoc-in mars-rover-data [:rover :direction] (get-in transforms [:right (get-in mars-rover-data [:rover :direction])])))

(defn move
  "Move rover forward one unit"
  [mars-rover-data]
  (let [direction (get-in mars-rover-data [:rover :direction])
        key (get-in transforms [:move direction :coord])
        op (get-in transforms [:move direction :action])]
    (assoc-in mars-rover-data [:rover key] (op (get-in mars-rover-data [:rover key])))))

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

(defn process-cmds
  "Reduces over a list of functions and builds up the rover's final position"
  ;; Uses fn-map to get the core functions corresponding to L, R and M commands
  [mars-rover-data]
  (reduce (fn [new-mars-rover-data cmd]
            (prn cmd)
            (prn new-mars-rover-data)
            (prn fn-map)
            ((resolve (get fn-map cmd)) new-mars-rover-data))
          mars-rover-data
          (map str (get mars-rover-data :cmd-sequence))))

;; (def mars-rover-data {:plateau-size {:x 10 :y 10}
;;                       :rover {:x 0
;;                               :y 0
;;                               :direction "N"}
;;                       :cmd-sequence "MMRMM"})

;; (process-cmds mars-rover-data)

;; mars-rover-data
;; transforms
;; (get fn-map "M")


(defn display-result
  [mars-rover-data]
  ;; keys destructuring
  (let [;;[x y d] (get mars-rover-data :rover)
        x (get-in mars-rover-data [:rover :x])
        y (get-in mars-rover-data [:rover :y])
        d (get-in mars-rover-data [:rover :direction])
        max-x (get-in mars-rover-data [:plateau-size :x])
        max-y (get-in mars-rover-data [:plateau-size :y])]
    (if (and (in-range? x 0 max-x) (in-range? y 0 max-y))
      (println "Final rover position:")
      (println "Final rover position outside plateau area, you destroyed the rover:"))
    (println x y d)))

(defn -main
  "Program entry point"
  [& args]
  ;;(println "Welcome to Mars!")
  ;;(display-result (process-cmds (process-user-input)))
  (pp/pprint fn-map))