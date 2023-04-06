;; Mars Rover Problem

(ns mars-rover.core
  (:require [clojure.string :refer :all])
  (:gen-class))

;; initialize, user-input
;; move, rotate

(defn user-input
  "Waits for 3 lines of input from user"
  []
  (let [plateau-size (trim (read-line))
        [plateau-size-x plateau-size-y] (map #(Integer/parseInt %) (split plateau-size #" "))
        rover-position (trim (read-line))
        [rover-x rover-y rover-direction] (split rover-position #" ")
        cmd-sequence (trim (read-line))]
    {:plateau-size {:x plateau-size-x
                    :y plateau-size-y}
     :rover {:x (Integer/parseInt rover-x)
             :y (Integer/parseInt rover-y)
             :direction rover-direction}
     :cmd-sequence cmd-sequence}))

(user-input)

(defn -main
  [& args]
  (println "Please enter input:")
  (user-input)
  )


    