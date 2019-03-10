(ns flatbond.helpers
  (:require [clojure.string :as string]))

(def configs {:client-1 {:fixed-membership-fee        true
                         :fixed-membership-fee-amount 100}
              :client-2 {:fixed-membership-fee        false
                         :fixed-membership-fee-amount 200}})

(defn- validate-rent
  [rent]
  (let [{:keys [min max]} {:min 25 :max 2000}]
    (and (integer? rent) (<= min (/ rent 100) max))))

(def ^:private postcode-regex #"^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$")

(defn- validate-postcode
  [postcode]
  (and (seq postcode) (re-matches postcode-regex (string/upper-case postcode))))

(defn- get-client-fee
  [id]
  (-> (get-in configs [id :fixed-membership-fee-amount])
      (* 1.2)
      (* 100)
      int))

(defn- validate-membership-fee
  [fee rent client-id]
  (let [clientid (keyword client-id)
        fixed? (get-in configs [clientid :fixed-membership-fee])
        client-fee (if fixed?
                     (get-client-fee (keyword client-id))
                     (-> rent (* 1.2) Math/round int))]
    (= fee client-fee)))

(defn validate-data
  [{:keys [rent membership-fee postcode client-id] :as params}]
  (and (validate-rent rent)
       (validate-postcode postcode)
       (validate-membership-fee membership-fee rent client-id)))