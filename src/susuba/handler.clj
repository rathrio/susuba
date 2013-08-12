(ns susuba.handler
  (:use compojure.core
        ring.middleware.json
        ring.util.response
        ring.middleware.cors
        [ring.adapter.jetty :only [run-jetty]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [susuba.db :as db]
            [susuba.beer :as beer]))

(defroutes app-routes
  (context "/beers" [] (defroutes beers-routes
    (GET "/" [] (beer/all))
    (POST "/" {body :body} (beer/create))
    (context "/:id" [id] (defroutes beer-routes
      (GET "/" [] (beer/find id))))))

  (GET "/" [] "Welcome to the dark side. There's nothing to see here.")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
    (wrap-json-body)
    (wrap-json-response)
    (wrap-cors
       :access-control-allow-origin #"http://localhost:4567")))

(defn- port []
  (Integer/parseInt (or (System/getenv "PORT") "3000")))

(defn -main []
  (db/maybe-init)
  (run-jetty app {:port (port)}))
