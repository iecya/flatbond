(ns flatbond.helpers
  (:require [flatbond.config :as config]
            [re-frame.core :as re-frame]
            [clojure.string :as string]))

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

(def ^:private postcode-regex #"^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$")

(defn- validate-postcode
  [{postcode :postcode}]
  (if (and (seq postcode) (re-matches postcode-regex (string/upper-case postcode)))
    true
    (re-frame/dispatch [:update-input-error :postcode])))

(defn validate-form
  [{:keys [flatbond-form] :as db}]
  (and (validate-rent flatbond-form)
       (validate-postcode flatbond-form)))


