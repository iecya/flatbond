(ns flatbond.helpers)

{:fixed-membership-fee        false
 :fixed-membership-fee-amount 200}

(defn to-decimal
  [value]
  (if (number? value)
    (let [factor (Math/pow 10 2)]
      (/ (Math/round (* value factor)) factor))
    value))

(defn add-vat
  [value]
  (to-decimal (* value 1.2)))

(defn calculate-membership
  [period value config]
  (cond
    (:fixed-membership-fee config) (:fixed-membership-fee-amount config)
    (= :weekly period) (max 120 value)
    :default (max 120 (-> value (* 12) (/ 52) to-decimal))))


