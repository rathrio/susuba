(ns susuba.beer
  (:require [somnium.congomongo :as m]))

(defn maybe-init []
  (or (m/collection-exists? :beers)
      (m/create-collection! :beers))

  (m/add-index! :beers [:name] :unique true)
  (m/add-index! :beers [:alc]))

(defn all []
  (m/fetch :beers :as :json))

(defn create [] ())

(defn find [id] ())

