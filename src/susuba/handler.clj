(ns susuba.handler
  (:use compojure.core
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware.json]
        [ring.util.response])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [susuba.db :as db]
            [susuba.beer :as beer]))

(defroutes app-routes
  (context "/beers" [] (defroutes beer-routes
    (GET "/" [] (beer/all))))

  (GET "/" [] "Hello this is dawg.")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
    (wrap-json-body)
    (wrap-json-response)))

(defn- port []
  (Integer/parseInt (or (System/getenv "PORT") "3000")))

(defn -main []
  (db/maybe-init)
  (run-jetty app {:port (port)}))
