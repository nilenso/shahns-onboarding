;; Mars Rover Problem

;; todo: input validation, check invalid moves

(ns mars-rover.core
  (:require [clojure.string :as string])
  (:gen-class))

(declare get-initial-position, get-cmd-sequence, transforms, fn-map)

(defn get-input
  []
  (let [input (string/trim (read-line))]
    (string/upper-case input)))

(defn get-plateau-size
  "Read one line of input stream to get upper right coordinates from user"
  []
  (let [xy-string (get-input)
        [x y] (map #(Integer/parseInt %) (string/split xy-string #" "))]
    (get-initial-position {:plateau-size {:x x
                                          :y y}})))

(defn get-initial-position
  "Read one line of input stream to get initial rover position"
  [info-map]
  (let [intial-position (get-input)
        [x y d] (string/split intial-position #" ")]
    (get-cmd-sequence (assoc-in info-map [:rover] {:x (Integer/parseInt x)
                                                   :y (Integer/parseInt y)
                                                   :direction d}))))

(defn get-cmd-sequence
  "Read one line of input stream to get sequence of commands for rover movement"
  [info-map]
  (let [cmd-sequence (get-input)]
    (assoc-in info-map [:cmd-sequence] cmd-sequence)))

(defn rotate-left
  "Rotate rover counter-clockwise 90 degrees"
  [info-map]
  (assoc-in info-map [:rover :direction] (get-in transforms [:left (get-in info-map [:rover :direction])])))

(defn rotate-right
  "Rotate rover clockwise 90 degrees"
  [info-map]
  (assoc-in info-map [:rover :direction] (get-in transforms [:right (get-in info-map [:rover :direction])])))

(defn move
  "Move rover forward one unit"
  [info-map]
  (let [[key op] (get-in transforms [:move (get-in info-map [:rover :direction])])]
    (assoc-in info-map [:rover key] ((resolve op) (get-in info-map [:rover key])))))


(defn process-cmds
  "Reduces over a list of functions and builds up the rover's final position"
  ;; Uses fn-map to get the core functions corresponding to L, R and M commands
  [info-map]
  (reduce (fn [new-info-map cmd] ((get fn-map cmd) new-info-map))
          info-map
          (map str (get info-map :cmd-sequence))))

;; Map defining left, right and move transforms
;; key -> current location; value -> location after applying transform
(def transforms {:left {"N" "W"
                        "E" "N"
                        "S" "E"
                        "W" "S"}
                 :right {"N" "E"
                         "E" "S"
                         "S" "W"
                         "W" "N"}
                 :move {"N" '(:y inc)
                        "E" '(:x inc)
                        "S" '(:y dec)
                        "W" '(:x dec)}})

;; Mapping of L, R and M to their respective core functions
(def fn-map {"L" mars-rover.core/rotate-left
             "R" mars-rover.core/rotate-right
             "M" mars-rover.core/move})

(defn -main
  [& args]
  (println "Please enter input:")
  (println (process-cmds (get-plateau-size))))

;; Testing

;; (def info-map {:plateau-size {:x 10 :y 10}
;;                :rover {:x 0 :y 0 :direction "N"}
;;                :cmd-sequence "LLRMMLLMMLMM"})
;; info-map
;; (process-cmds info-map)