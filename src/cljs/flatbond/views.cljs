(ns flatbond.views
  (:require
    [re-frame.core :as re-frame]
    [flatbond.subs :as subs]
    [reagent.core :as reagent]
    [flatbond.components.rent-input :as rent-input]
    [flatbond.helpers :as helpers]
    [flatbond.components.membership-input :as membership-input]
    [flatbond.components.postcode-input :as postcode-input]
    [clojure.string :as string]))

(defn creation-form []
  (let [error (re-frame/subscribe [::subs/flatbond-page-error])
        config (re-frame/subscribe [::subs/user-config])
        rent-period (re-frame/subscribe [::subs/rent-period])
        rent-value (re-frame/subscribe [::subs/rent-value])
        membership-fee (re-frame/subscribe [::subs/membership-fee])
        postcode (re-frame/subscribe [::subs/postcode])
        errors (re-frame/subscribe [::subs/form-errors])]
    (reagent/create-class
      {:component-did-mount (fn []
                              (re-frame/dispatch [:get-config]))
       :reagent-render      (fn []
                              (let [membership-fee-amount (if (:fixed-membership-fee @config)
                                                            (:fixed-membership-fee-amount @config)
                                                            @membership-fee)]
                                [:div.app-section
                                 [:h1 "Create a new Flatbond"]

                                 (if @error
                                   [:div
                                    [:h2 "Ops, something went wrong!"]
                                    [:p "Please try again later"]]

                                   [:form
                                    [:div.form-row
                                     [rent-input/input :weekly @rent-period @rent-value @errors]
                                     [rent-input/input :monthly @rent-period @rent-value @errors]]
                                    [:div.form-row
                                     [membership-input/input (helpers/add-vat membership-fee-amount)]]
                                    [:div.form-row
                                     [postcode-input/input @postcode @errors]]
                                    [:div.form-row
                                     [:button.btn.btn-primary {:type     "submit"
                                                               :on-click #(do
                                                                            (.preventDefault %)
                                                                            (re-frame/dispatch [:submit-form]))} "Submit"]]])]))})))


;; about

(defn details-page
  []
  (let [flatbond (re-frame/subscribe [::subs/flatbond])]
    [:div
     [:h1 "Flatbond summary"]

     [:div
      "Thanks for submitting your flatbond"

      [:div.app-section

       [:div.row.flatbond-summary
        [:div.col-sm-3.flatbond-title "Rent (weekly)"]
        [:div.col-sm-3 (str "£" (-> (@flatbond "rent") (/ 100) Math/round))]]
       [:div.row
        [:div.col-sm-3.flatbond-title "Membership fee (VAT Inc.)"]
        [:div.col-sm-3 (str "£" (-> (@flatbond "membership-fee") (/ 100) Math/round))]]
       [:div.row
        [:div.col-sm-3.flatbond-title "Postcode"]
        [:div.col-sm-3 (string/upper-case (@flatbond "postcode"))]]]

      [:a {:href "#/"}
       "go to Home Page"]]]))


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
