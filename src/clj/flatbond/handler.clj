(ns flatbond.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]))

(def configs {:client-1 {:fixed-membership-fee        true
                         :fixed-membership-fee-amount 100}
              :cliend-2 {:fixed-membership-fee        false
                         :fixed-membership-fee-amount 200}})


(defroutes routes
  (GET "/config/:client-id" [client-id]
    (if-let [config (get configs (keyword client-id))]
      {:status 200
       :body config}
      {:status 404
       :body "User data not found"}))
  (GET "/config" []
    {:status 200
     :body configs})
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
