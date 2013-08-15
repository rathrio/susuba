(ns susuba.beers
  (:use cheshire.core
        ring.util.response)
  (:require monger.json
            [clojure.tools.logging :as lg]
            [monger.collection :as mc]
            [susuba.db :as db])
  (:import [org.bson.types ObjectId]))

(defn all []
  (response {:beers (mc/find-maps "beers")}))

(defn create [body]
  (let [oid (ObjectId.)
        doc (body "beer")]
    (response {:beer (mc/insert-and-return "beers" (merge doc {:_id oid}))})))

(defn update [id body]
  (let [oid (ObjectId. id)
        doc (body "beer")]
    (mc/update-by-id "beers" oid doc)
    (find_by_id id)))

(defn find_by_id [id]
  (let [oid (ObjectId. id)]
    (response (mc/find-one-as-map "beers" {:_id oid}))))
