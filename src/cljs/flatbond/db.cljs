(ns flatbond.db)

(def default-db
  {:name          "Flatbond"
   :client-id     :client-1
   :flatbond-form {:rent-period :weekly
                   :rent-value  {:weekly  25
                                 :monthly 110}}
   :membership-fee 120})
