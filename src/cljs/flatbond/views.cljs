(ns flatbond.views
  (:require
   [re-frame.core :as re-frame]
   [flatbond.subs :as subs]
   [reagent.core :as reagent]))


;; home

(defn creation-form []
  (let [error (re-frame/subscribe [::subs/flatbond-page-error])
        config (re-frame/subscribe [::subs/user-config])
        rent-period (re-frame/subscribe [::subs/rent-period])
        rent-value (re-frame/subscribe [::subs/rent-value])]
    (reagent/create-class
      {:component-did-mount (fn []
                              (re-frame/dispatch [:get-config]))
       :reagent-render      (fn []
                              (let [weekly? (= :weekly @rent-period)
                                    monthly? (not weekly?)]
                                [:div
                                 [:h1 "Create a new Flatbond"]

                                 [:form
                                  [:div.form-row
                                   [:div.col
                                    [:div.custom-control.custom-radio
                                     [:input {:type      "radio"
                                              :id        "rent-weekly"
                                              :name      "rent-period"
                                              :className "custom-control-input"
                                              :checked   weekly?
                                              :value     "weekly"
                                              :onChange  #(re-frame/dispatch [:update-rent-period (-> % .-target .-value)])}]
                                     [:label {:className "custom-control-label"
                                              :for       "rent-weekly"} "Weekly rent (£)" [:span.rent-range-info "(Min £25 - Max £2000)"]]]
                                    [:div.form-group
                                     [:label {:className "sr-only"
                                              :for       "weekly-rent-value"} "Weekly rent"]
                                     [:input {:type        "number"
                                              :class       "form-control"
                                              :placeholder "Enter your weekly rent"
                                              :disabled    monthly?
                                              :onChange    #(re-frame/dispatch [:update-rent-value :weekly (-> % .-target .-value)])
                                              :value       (:weekly @rent-value)
                                              :min         25
                                              :max         2000}]]]
                                   [:div.col
                                    [:div.custom-control.custom-radio
                                     [:input {:type      "radio"
                                              :id        "rent-monthly"
                                              :name      "rent-period"
                                              :className "custom-control-input"
                                              :checked   (= :monthly @rent-period)
                                              :value     "monthly"
                                              :onChange  #(re-frame/dispatch [:update-rent-period (-> % .-target .-value)])}]
                                     [:label {:className "custom-control-label"
                                              :for       "rent-monthly"} "Monthly rent (£)" [:span.rent-range-info "(Min £110 - Max £8660)"]]]
                                    [:div.form-group
                                     [:label {:className "sr-only"
                                              :for       "monthly-rent-value"} "Monthly rent"]
                                     [:input {:type        "number"
                                              :className   "form-control"
                                              :disabled    (= :weekly @rent-period)
                                              :placeholder "Enter your monthly rent"
                                              :onChange    #(re-frame/dispatch [:update-rent-value :monthly (-> % .-target .-value)])
                                              :value       (:monthly @rent-value)
                                              :min         110
                                              :max         8660}]]]]]
                                 ]))})))


;; about

(defn details-page []
  [:div
   [:h1 "This is the Details page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :flatbond-create [creation-form]
    :details-page [details-page]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div.container
     [show-panel @active-panel]]))
