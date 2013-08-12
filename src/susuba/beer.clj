(ns susuba.beer
  (:use cheshire.core)
  (:require monger.json
            [monger.collection :as mc]))

(defn all []
  (generate-string {:beers (mc/find-maps "beers")}))

(defn create [] ())

(defn find_by_id [id] ())

