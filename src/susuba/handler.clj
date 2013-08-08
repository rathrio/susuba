(ns susuba.handler
  (:use compojure.core
        [ring.adapter.jetty :only [run-jetty]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [susuba.db :as db]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/onebeer" [] "hello im beers")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn- get-port []
  (Integer/parseInt (or (System/getenv "PORT") "3000")))

(defn -main []
  (run-jetty app {:port (get-port)}))
