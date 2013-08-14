(ns susuba.handler
  (:use compojure.core
        ring.middleware.json
        ring.util.response
        ring.middleware.cors
        [ring.adapter.jetty :only [run-jetty]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.logger :as logger]
            [susuba.db :as db]
            [susuba.beers :as beers]))

(defroutes app-routes
  (context "/beers" []
    (GET "/" [] (beers/all))
    (POST "/" {body :body} (beers/create body))
    (context "/:id" [id]
      (GET "/" [] (beers/find_by_id id))))

  (GET "/" [] "Welcome to the dark side. There's nothing to see here.")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
    (logger/wrap-with-logger)
    (wrap-json-body)
    (wrap-json-response)
    (wrap-cors
       :access-control-allow-origin #"http://localhost:4567")))

(defn- port []
  (Integer/parseInt (or (System/getenv "PORT") "3000")))

(defn -main []
  (db/init)
  (run-jetty app {:port (port)}))
