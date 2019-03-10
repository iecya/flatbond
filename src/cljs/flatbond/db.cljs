(ns flatbond.db
  (:require [flatbond.config :as config]))

(def default-db
  {:name          "Flatbond"
   :client-id     :client-1
   :flatbond-form {:rent-period :weekly
                   :rent-value  {:weekly  25
                                 :monthly 110}
                   :postcode "sw18 1sf"
                   :membership-fee 120}
   :rent-range config/rent-ranges})
