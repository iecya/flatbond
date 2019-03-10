(ns flatbond.views
  (:require
   [re-frame.core :as re-frame]
   [flatbond.subs :as subs]
   [reagent.core :as reagent]
   [flatbond.components.rent-input :as rent-input]
   [flatbond.helpers :as helpers]
   [flatbond.components.membership-input :as membership-input]
   [flatbond.components.postcode-input :as postcode-input]))

(defn creation-form []
  (let [error (re-frame/subscribe [::subs/flatbond-page-error])
        rent-period (re-frame/subscribe [::subs/rent-period])
        rent-value (re-frame/subscribe [::subs/rent-value])
        membership-fee (re-frame/subscribe [::subs/membership-fee])
        postcode (re-frame/subscribe [::subs/postcode])]
    (reagent/create-class
      {:component-did-mount (fn []
                              (re-frame/dispatch [:get-config]))
       :reagent-render      (fn []
                              [:div
                               [:h1 "Create a new Flatbond"]

                               (if @error
                                 [:div
                                  [:h2 "Ops, something went wrong!"]
                                  [:p "Please try again later"]]

                                 [:form
                                  [:div.form-row
                                   [rent-input/input :weekly @rent-period @rent-value]
                                   [rent-input/input :monthly @rent-period @rent-value]]
                                  [:div.form-row
                                   [membership-input/input (helpers/add-vat @membership-fee)]]
                                  [:div.form-row
                                   [postcode-input/input @postcode]]])])})))


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
