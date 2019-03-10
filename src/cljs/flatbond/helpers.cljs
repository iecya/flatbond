(ns flatbond.helpers)

(defn to-decimal
  [value]
  (if (number? value)
    (let [factor (Math/pow 10 2)]
      (/ (Math/round (* value factor)) factor))
    value))

(defn calculate-membership
  [period value]
  (if (= :weekly period)
    (get value period)
    (-> value period (* 12) (/ 52) to-decimal)))
