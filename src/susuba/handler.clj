(ns susuba.handler
  (:use compojure.core
        [ring.adapter.jetty :only [run-jetty]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [susuba.db :as db]
            [susuba.beer :as beer]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/beers" [] (beer/all))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn- port []
  (Integer/parseInt (or (System/getenv "PORT") "3000")))

(defn -main []
  (db/maybe-init)
  (run-jetty app {:port (port)}))
