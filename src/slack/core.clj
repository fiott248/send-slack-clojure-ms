(ns slack.core
  (:require [compojure.core :refer [defroutes GET ANY]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as jetty]
            [slack.views.layout :as layout]
            [slack.views.contents :as contents]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn send-to-slack
 "Sends a simple message to slack using an 'incoming webhook'.
 url will be of form: https://myapp.slack.com/services/hooks/incoming-webhook?token=mytoken .
 (Exact url you should use will appear on the slack integration page)
 text will be any valid message.
 This implementation could be expanded if you wanted to specify channel, username, etc.
 For more information see:
 https://my.slack.com/services/new/incoming-webhook . (You'll need a slack account
 to see that)"
  [url text]
  (client/post url {:form-params {:payload (json/write-str {:text text})}}))


(defroutes routes
  (GET "/" [] (layout/application "Home" (contents/index)))
  (GET "/slack" [message] (layout/application "slack" (contents/slack [message])))
  ;(GET "/slack" [message] (send-to-slack "web-hook" message))
  (route/resources "/")
  (ANY "*" [] (route/not-found (layout/application "Page Not Found" (contents/not-found)))))

(def application (handler/site routes))



(defn -main []
(let [port (Integer/parseInt (or (System/getenv "PORT") "8090"))]
  (jetty/run-jetty application {:port port :join? false})))
