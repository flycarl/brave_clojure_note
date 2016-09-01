(ns brave_clojure_note.fwpd)

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

(convert :glitter-index "3")

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(parse (slurp filename))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(first (mapify (parse (slurp filename))))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))
(glitter-filter 3 (mapify (parse (slurp filename))))

;;; excise 1
(defn getName
  [dicList]
  (map #(:name %) dicList))

(getName (glitter-filter 3 (mapify (parse (slurp filename)))))

;;; excise 2
(defn append
  [suspects record]
  (conj suspects record))

(def suspect_records (mapify (parse (slurp filename))))
(append suspect_records {:name "dosme" :glitter-index "7"})

;;; excise 3


;;;exicise 4
(slurp filename)
(first suspect_records)

(defn int->str
  [num]
  (String. num))

(defn conver2String
  [record]
  (str (:name record) "," (:glitter-index record)))

(conver2String (first suspect_records))
(map conver2String suspect_records)
(clojure.string/join "\n" (map conver2String suspect_records))
