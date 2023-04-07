;; Mars Rover Problem

(ns mars-rover.core
  (:require [clojure.string :as string])
  (:gen-class))

(declare rotate-left, rotate-right, move, transforms, fn-map)

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

(defn rotate-left
  [info-map]
  (assoc-in info-map [:rover :direction] (get-in transforms [:left (get-in info-map [:rover :direction])])))

(defn rotate-right
  [info-map]
  (assoc-in info-map [:rover :direction] (get-in transforms [:right (get-in info-map [:rover :direction])])))

(defn move
  [info-map]
  (let [[key op] (get-in transforms [:move (get-in info-map [:rover :direction])])] 
    (assoc-in info-map [:rover key] ((resolve op) (get-in info-map [:rover key])))))

(defn process-cmds
  [info-map]
  (reduce (fn [new-info-map cmd] ((get fn-map cmd) new-info-map))
          info-map
          (map str (get info-map :cmd-sequence))))

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

(def fn-map {"L" mars-rover.core/rotate-left
             "R" mars-rover.core/rotate-right
             "M" mars-rover.core/move})

(defn -main
  [& args]
  (println "Please enter input:")
  (println (process-cmds (user-input))))

;; Testing

;; (def info-map {:plateau-size {:x 10 :y 10}
;;                :rover {:x 0 :y 0 :direction "N"}
;;                :cmd-sequence "LLRMMLLMMLMM"})
;; info-map
;; (process-cmds info-map)