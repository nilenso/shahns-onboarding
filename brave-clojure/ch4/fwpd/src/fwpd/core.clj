;; A Vampire Data Analysis Program for the FWPD

(ns fwpd.core)

(def filename "suspects.csv")
(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Returns a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;; 1. Turn the result of your glitter filter into a list of names.
(def result (glitter-filter 3(mapify (parse (slurp filename)))))
(into '() (map :name result))

;; 2. Write a function, append, which will append a new suspect to your list of suspects.
(defn append
  []
  )
;; 3. Write a function, validate, which will check that :name and :glitter-index are present when you append. The validate function should accept two arguments: a map of keywords to validating functions, similar to conversions, and the record to be validated.

(defn validate
  [])
;; 4. Write a function that will take your list of maps and convert it back to a CSV string. You’ll need to use the clojure.string/join function.





;; testing
(glitter-filter 3 (mapify (parse (slurp filename))))

(mapify (parse (slurp filename)))
  


(parse (slurp filename))
;; testing
(slurp filename)
(def temp (clojure.string/split (slurp filename) #"\n"))
temp 
(def temp1 (map #(clojure.string/split % #",") temp))
temp1
(mapify temp1)

(map vector vamp-keys ["Edward Cullen" "10"])