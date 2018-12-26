(defproject slack "0.1.0-SNAPSHOT"
  :description "Slack wrapper"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [compojure "1.1.6"]
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-jetty-adapter "1.2.1"]
                 [clj-http "1.0.0"]]
  :main slack.core
  :ring {:handler slack.core/application})
