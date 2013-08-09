(ns susuba.db
  (:require [somnium.congomongo :as m]
            [susuba.beer :as beer])
  (:use [somnium.congomongo.config :only [*mongo-config*]]))

(defn maybe-init
  "Connects to a collection if it exists, creates it otherwise."
  []
  (when-not (m/connection? *mongo-config*)
    (let [mongo-url (get (System/getenv) "MONGOHQ_URL" "mongodb://localhost/susuba")]
      (m/set-connection! (m/make-connection mongo-url))
      (m/set-write-concern *mongo-config* :safe)
      (beer/maybe-init))))

