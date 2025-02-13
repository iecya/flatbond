(ns flatbond.db
  (:require [flatbond.config :as config]))

(def default-db
  {:name          "Flatbond"
   :client-id     1
   :flatbond-form {:rent-period :weekly
                   :rent-value  {:weekly  25
                                 :monthly 110}
                   :postcode ""}
   :rent-range config/rent-ranges})
