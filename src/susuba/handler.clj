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

(def options-response
  {:status 200
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body "Hello Stupid API Client"})

(defroutes app-routes
  (context "/beers" []
    (OPTIONS "/random" [] options-response)
    (GET "/random" [] (beers/random))
    (GET "/" [] (beers/index))
    (OPTIONS "/" [] options-response)
    (POST "/" {body :body} (beers/create body))
    (context "/:id" [id]
      (GET "/" [] (beers/find-by-id id))
      (OPTIONS "/" [] options-response)
      (PUT "/" {body :body} (beers/update id body))
      (DELETE "/" [] (beers/delete id))))
  (GET "/" [] (ring.util.response/redirect "/index.html"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
    (logger/wrap-with-logger)
    (wrap-json-body)
    (wrap-json-response {:pretty true})
    (cors/wrap-cors
      :access-control-allow-origin #".*"
      :access-control-allow-methods ["POST" "PUT" "DELETE"]
      :access-control-allow-headers
        ["Origin" "X-Requested-With" "Content-Type" "Accept"])))

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
