(ns flatbond.views
  (:require
   [re-frame.core :as re-frame]
   [flatbond.subs :as subs]
   ))


;; home

(defn creation-form []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Flatbond creation form.")]

     [:div
      [:a {:href "#/about"}
       "go to About Page"]]
     ]))


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
