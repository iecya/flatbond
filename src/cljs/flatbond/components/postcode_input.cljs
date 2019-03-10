(ns flatbond.components.postcode-input
  (:require [re-frame.core :as re-frame]))

(defn input
  [postcode errors]
  [:div.col
   [:div.form-group
    [:label {:for       "postcode"} "Postcode"]
    [:input {:type        "text"
             :class       "form-control"
             :placeholder (str "Enter your postcode")
             :onChange    #(re-frame/dispatch [:update-postcode type (-> % .-target .-value)])
             :value       postcode}]
    (when (:postcode errors)
      [:div.text-danger "Invalid postcode"])]])
