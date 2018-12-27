(ns slack.core
  (:require [compojure.core :refer [defroutes GET POST ANY]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [slack.views.layout :as layout]
            [slack.views.contents :as contents]
            [clj-http.client :as client]
            [clojure.data.json :as json])
  (:gen-class)
            )

(defroutes routes
  (GET "/" [] (layout/application "Home" (contents/index)))
  (GET "/slack" [] (layout/application "slack" (contents/slack)))
  (POST "/slack" [hook text] (contents/send-to-slack hook text) {:status 302 :headers {"Location" "/slack"}})
  ;(GET "/slack" [message] (send-to-slack "web-hook" message))
  (route/resources "/")
  (ANY "*" [] (route/not-found (layout/application "Page Not Found" (contents/not-found)))))

(def application (handler/site routes))



(defn -main []
(let [port (Integer/parseInt (or (System/getenv "PORT") "8090"))]
  (jetty/run-jetty application {:port port :join? false})))
