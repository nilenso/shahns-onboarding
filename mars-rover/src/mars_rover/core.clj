;; Mars Rover Problem

(ns mars-rover.core
  (:require [clojure.string :as string])
  (:gen-class))

;; todo: input validation, check invalid moves

(defn user-input
  "Waits for 3 lines of input from user"
  []
  (let [plateau-size (string/trim (read-line))
        [plateau-size-x plateau-size-y] (map #(Integer/parseInt %) (string/split plateau-size #" "))
        rover-position (string/trim (read-line))
        [rover-x rover-y rover-direction] (string/split rover-position #" ")
        cmd-sequence (string/trim (read-line))]
    {:plateau-size {:x plateau-size-x
                    :y plateau-size-y}
     :rover {:x (Integer/parseInt rover-x)
             :y (Integer/parseInt rover-y)
             :direction rover-direction}
     :cmd-sequence cmd-sequence}))

(def transform-left {"N" "W"
                     "E" "N"
                     "S" "E"
                     "W" "S"})

(def transform-right {"N" "E"
                      "E" "S"
                      "S" "W"
                      "W" "N"})

(def transform-move {"N" "y inc"
                     "E" "x inc"
                     "S" "y dec"
                     "W" "x dec"})

(def fn-map {"L" "rotate-left"
             "R" "rotate-right"
             "M" "move"})

(defn rotate-left
  [info-map]
  (assoc-in info-map [:rover :direction] (get transform-left (get-in info-map [:rover :direction]))))

(defn rotate-right
  [info-map]
  (assoc-in info-map [:rover :direction] (get transform-right (get-in info-map [:rover :direction]))))

(defn move
  [info-map]
  (let [transformation (get transform-move (get-in info-map [:rover :direction]))
        [key op] (string/split transformation #" ")]
    (assoc-in info-map [:rover (keyword key)] ((resolve (symbol op)) (get-in info-map [:rover (keyword key)])))))

(defn process-cmds
  [info-map]
  (reduce (fn [rover-map rover-fn] ((resolve (symbol (get fn-map rover-fn))) rover-map))
          info-map
          (map str (get info-map :cmd-sequence))))

(defn -main
  [& args]
  (println "Please enter input:")
  (println (process-cmds (user-input))))

;; Testing

;; (def info-map {:plateau-size {:x 10 :y 10}
;;                :rover {:x 0 :y 0 :direction "N"}
;;                :cmd-sequence "LLRRMM"})
;; info-map
;; (process-cmds info-map)