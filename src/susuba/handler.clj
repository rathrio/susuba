(ns susuba.handler
  (:use compojure.core
        cheshire.core
        ring.middleware.json
        ring.util.response
        [ring.adapter.jetty :only [run-jetty]])
  (:require [ring.middleware.cors :as cors]
            [compojure.handler :as handler]
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
    (wrap-json-response {:pretty true})
    (cors/wrap-cors
      :access-control-allow-origin #".*"
      :access-control-allow-headers ["Origin" "X-Requested-With" "Content-Type" "Accept"])))


(defn- port []
  (Integer/parseInt (or (System/getenv "PORT") "3000")))

(defn -main
  "This function will be called when one executes
   'lein run'. I have yet to find out how to start
   the ring server on heroku without providing a
   main function."
  []
  (db/init)
  (run-jetty app {:port (port)}))
