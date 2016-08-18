(ns brave-clojure-note.chapter3)
;exercise 4
(def mapset (comp set map))
(mapset inc [1 1 2 2])

(mapset + [3 4 2] [1 1 2 2])

;exercise 5
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])
(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(better-symmetrize-body-parts asym-hobbit-body-parts)

(defn replaceOnePart
  [part key]
  (clojure.string/replace part #"^left-" (str key "-")))

(replaceOnePart "left-eye" "right")

(defn matching-alien-part
  [part key]
  {:name (replaceOnePart (:name part) key)
   :size (:size part)})

(matching-alien-part {:name "left-eye" :size 1} "right")

(defn matching-all-alien-part
  [part]
  (map #(matching-alien-part part %) ["right" "left2" "right2" "middle"]))

(matching-all-alien-part {:name "left-eye" :size 1})

(defn better-symmetrize-alien-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (conj (matching-all-alien-part part) part))))
          []
          asym-body-parts))
(better-symmetrize-alien-body-parts asym-hobbit-body-parts)

;;;exercise 6
(defn matching-several-part
  [part num]
  (map #(matching-alien-part part (str "part" %)) (take (dec num) (range)) ))

(matching-several-part (nth asym-hobbit-body-parts 1) 2)
(defn better-symmetrize-several-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts num]
  (reduce (fn [final-body-parts part]
            (into final-body-parts
                  (set (conj (matching-several-part part num) part))))
          []
          asym-body-parts))

(better-symmetrize-several-body-parts asym-hobbit-body-parts 4)
