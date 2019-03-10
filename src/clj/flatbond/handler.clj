(ns flatbond.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [flatbond.helpers :as helpers]

            [ring.middleware.format :refer [wrap-restful-format]]))

(def flatbonds (atom []))

(defroutes routes
  (GET "/config/:client-id" [client-id]
    (if-let [config (helpers/get-config (Integer. client-id))]
      {:status 200
       :body (dissoc config :id)}
      {:status 404
       :body "User data not found"}))
  (GET "/config" []
    {:status 200
     :body helpers/configs})
  (POST "/flatbond" req
    (let [params (:params req)]
      (if (helpers/validate-data params)
        (do
          (swap! flatbonds conj params)
          {:status 200
           :body   (last @flatbonds)})
        {:status 500
         :body   "Invalid data"})))
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (resources "/"))

(def dev-handler (-> #'routes
                     (wrap-restful-format :formats [:json-kw :edn])
                     wrap-json-response
                     wrap-reload))

(def handler dev-handler)
