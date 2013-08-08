(ns susuba.db
  (:require [somnium.congomongo :as m]))

(def conn
  (m/make-connection "susuba"))

(m/set-connection! conn)
