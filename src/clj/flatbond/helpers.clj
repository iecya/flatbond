(ns flatbond.helpers
  (:require [clojure.string :as string]))

(def configs [{:id 1
               :fixed-membership-fee        true
               :fixed-membership-fee-amount 100}
              {:id 2
               :fixed-membership-fee        false
               :fixed-membership-fee-amount 200}])

(defn get-config
  [id]
  (first (filter #(= id (:id %)) configs)))

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
  (println "Id: " id)
  (-> (get-config id)
      :fixed-membership-fee-amount
      (* 1.2)
      (* 100)
      int))

(defn- validate-membership-fee
  [fee rent client-id]
  (let [fixed? (-> client-id Integer. get-config :fixed-membership-fee)
        client-fee (if fixed?
                     (get-client-fee (Integer. client-id))
                     (-> rent (* 1.2) Math/round int))]
    (= fee client-fee)))

(defn validate-data
  [{:keys [rent membership-fee postcode client-id] :as params}]
  (and (validate-rent rent)
       (validate-postcode postcode)
       (validate-membership-fee membership-fee rent client-id)))