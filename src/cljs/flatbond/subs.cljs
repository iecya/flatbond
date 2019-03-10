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
