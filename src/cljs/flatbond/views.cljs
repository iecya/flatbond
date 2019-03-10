(ns flatbond.views
  (:require
   [re-frame.core :as re-frame]
   [flatbond.subs :as subs]
   [reagent.core :as reagent]))


;; home

(defn creation-form []
  (let [error (re-frame/subscribe [::subs/flatbond-page-error])
        config (re-frame/subscribe [::subs/user-config])]
    (reagent/create-class
      {:component-did-mount (fn []
                              (re-frame/dispatch [:get-config]))
       :reagent-render (fn []
                    [:div
                     [:h1 (str "Hello from " @error ". This is the Flatbond creation form.")]

                     [:div (str "Fixed fee? " (:fixed-membership-fee @config))]

                     [:div
                      [:a {:href "#/about"}
                       "go to About Page"]]
                     ])})))


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
    [show-panel @active-panel]))
