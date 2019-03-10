(ns flatbond.helpers
  (:require [flatbond.config :as config]
            [re-frame.core :as re-frame]))

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

(defn- validate-rent
  [{:keys [rent-period rent-value]}]
  (let [{:keys [min max]} (get config/rent-ranges rent-period)]
    (if-not (<= min (get rent-value rent-period) max)
      (do (re-frame/dispatch [:update-input-error rent-period])
          false)
      true)))

(defn validate-form
  [{:keys [flatbond-form] :as db}]
  (and (validate-rent flatbond-form)
       #_(validate-postcode)))


