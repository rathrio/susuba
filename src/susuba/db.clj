(ns susuba.db
  (:require [somnium.congomongo :as m]))

(def conn
  (m/make-connection "susuba"))

(m/set-connection! conn)

(m/insert! :beers
           {:name "Bier Bienne 3"})

(defn one-beer []
  m/fetch-one :beers)
