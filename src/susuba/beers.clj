(ns susuba.beers
  (:use cheshire.core
        ring.util.response)
  (:require monger.json
            [clojure.tools.logging :as lg]
            [monger.collection :as mc]
            [susuba.db :as db])
  (:import [org.bson.types ObjectId]))

(defn index []
  (response {:beers (mc/find-maps "beers")}))

(defn create [body]
  (let [oid (ObjectId.)
        doc (body "beer")]
    (response {:beer (mc/insert-and-return "beers" (merge doc {:_id oid}))})))

(defn find-by-id [id]
  (let [oid (ObjectId. id)]
    (response {:beer (mc/find-one-as-map "beers" {:_id oid})})))

(defn update [id body]
  (let [oid (ObjectId. id)
        doc (body "beer")]
    (mc/update-by-id "beers" oid doc)
    (find-by-id id)))

(defn delete [id]
  (let [oid (ObjectId. id)]
    (mc/remove-by-id "beers" oid)
    {:status 204}))

(defn random
  "Returns a random beer"
  []
  (response {:beer (rand-nth (mc/find-maps "beers"))}))
