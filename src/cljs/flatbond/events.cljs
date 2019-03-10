(ns flatbond.events
  (:require
   [re-frame.core :as re-frame]
   [flatbond.db :as db]
   [ajax.core :refer [GET]]))


(def request-options
  {:response-format  :json
   :keywords?        true
   :timeout 60000})



(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
  :get-config
  (fn [db]
    (GET (str "/config/" (-> db :client-id name))
              (merge request-options
                     {:handler #(re-frame/dispatch [:update-config %1])
                      :error-handler #(re-frame/dispatch [:update-flatbond-error %1])}))
    db))

(re-frame/reg-event-db
  :update-config
  (fn [db [_ config]]
    (assoc db :user-config config)))

(re-frame/reg-event-db
  :update-flatbond-error
  (fn [db [_ error-msg]]
    (assoc db :flatbond-page-error error-msg)))



;; FORM EVENTS

(re-frame/reg-event-db
  :update-rent-period
  (fn [db [_ period]]
    (assoc-in db [:flatbond-form :rent-period] (keyword period))))

(re-frame/reg-event-db
  :update-rent-value
  (fn [db [_ period value]]
    (assoc-in db [:flatbond-form :rent-value period] value)))