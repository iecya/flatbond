(ns flatbond.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
  ::user-config
  (fn [db _]
    (:user-config db)))

(re-frame/reg-sub
  ::flatbond-page-error
  (fn [db _]
    (:flatbond-page-error db)))

(re-frame/reg-sub
  ::rent-period
  (fn [db _]
    (get-in db [:flatbond-form :rent-period])))

(re-frame/reg-sub
  ::rent-value
  (fn [db _]
    (get-in db [:flatbond-form :rent-value])))

(re-frame/reg-sub
  ::membership-fee
  (fn [db _]
    (:membership-fee db)))

(re-frame/reg-sub
  ::postcode
  (fn [db _]
    (:postocde db)))

(re-frame/reg-sub
  ::form-errors
  (fn [db _]
    (:form-errors db)))
