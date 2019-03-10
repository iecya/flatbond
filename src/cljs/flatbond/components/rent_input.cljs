(ns flatbond.components.rent-input
  (:require [re-frame.core :as re-frame]
            [clojure.string :as string]))


(def labels-info
  {:weekly "(Min £25 - Max £2000)"
   :monthly "(Min £110 - Max £8660)"})

(def rent-range
  {:weekly {:min 25 :max 2000}
   :monthly {:min 110 :mac 8660}})

(defn input
  [type active-type rent-value]
  (let [rent-type (name type)]
    [:div.col
     [:div.custom-control.custom-radio
      [:input {:type      "radio"
               :id        (str "rent-" rent-type)
               :name      "rent-period"
               :className "custom-control-input"
               :checked   (= type active-type)
               :value     rent-type
               :onChange  #(re-frame/dispatch [:update-rent-period (-> % .-target .-value)])}]
      [:label {:className "custom-control-label"
               :for       (str "rent-" rent-type)} (str (string/capitalize rent-type) " rent (£)") [:span.rent-range-info (get labels-info type)]]]
     [:div.form-group
      [:label {:className "sr-only"
               :for       (str rent-type "-rent-value")} (str (string/capitalize rent-type) " rent")]
      [:input {:type        "number"
               :class       "form-control"
               :placeholder (str "Enter your " rent-type " rent")
               :disabled    (not= type active-type)
               :onChange    #(re-frame/dispatch [:update-rent-value type (-> % .-target .-value)])
               :value       (get rent-value type)
               :min         (get-in rent-range [type :min])
               :max         (get-in rent-range [type :max])}]]]))
