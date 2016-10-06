(ns brave-clojure-note.chapter5)

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(def comp-test (two-comp inc #(/ % 2)))

(comp-test 10)

(defn my-comp
  [& args]
  (reduce two-comp args))

(def someComp (comp inc inc #(/ % 2) #(* % 3)))
(someComp 10)

(def someComp (my-comp inc inc #(/ % 2) #(* % 3)))
(someComp 10)
