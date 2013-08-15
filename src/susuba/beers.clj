(ns susuba.beers
  (:use cheshire.core
        ring.util.response)
  (:require monger.json
            [monger.collection :as mc]
            [susuba.db :as db]))

(defn all []
  (response {:beers (mc/find-maps "beers")}))

(defn create [body]
  (clojure.tools.logging/info (str body)))

(defn find_by_id [id] ())
