(ns susuba.db
  (:require [monger.core :as mg]))

(def mongo-url (System/getenv "MONGOHQ_URL"))

(defn init
  "Connects to the db via URI if it can be found in the 'MONGOHQ_URL'
  env variable, connects to localhost otherwise."
  []
  (if mongo-url
    (mg/connect-via-uri! mongo-url)
    (do
      (mg/connect!)
      (mg/set-db! (mg/get-db "susuba")))))
