(ns flatbond.components.membership-input
  (:require [re-frame.core :as re-frame]
            [clojure.string :as string]))

(defn input
  [value]
  [:div.col
   [:div.form-group
    [:div.form-inline
     [:label {:for "membership-fee"} "Membership fee:"]
     [:input {:type      "text"
              :read-only true
              :class     "form-control form-control-plaintext"
              ;:onChange    #(re-frame/dispatch [:update-membership-fee (-> % .-target .-value)])
              :value     (str "£" value)}]]
    [:p.label-info "(VAT Included)"]]])
