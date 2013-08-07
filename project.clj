(defproject susuba "0.1.0-SNAPSHOT"
  :description "Susuba Backend"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :main susuba.handler
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [congomongo "0.4.1"]
                 [ring "1.2.0"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler susuba.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
