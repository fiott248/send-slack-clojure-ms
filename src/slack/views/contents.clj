(ns slack.views.contents
  (:use [hiccup.form]
        [hiccup.element :only (link-to)])
  (:require [clj-http.client :as client]
            [clojure.data.json :as json])
        )

(defn index []
  [:div {:id "content"}
   [:h1 {:class "text-success"} "Hello Hiccup"]]
  )

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

(defn is-message? [message]
   (if (message) (send-to-slack "web-hook" message)))

(defn slack [message]
  [:div {:class "well"}
   [:form {:role "form"}
     [:div {:class "form-group"}
      (label {:class "control-label"} "slack-field" "Slack Message")
      (text-field {:class "form-control" :placeholder "text area" :ng-model "text"} "message")]]]
  ;(is-message? message) //function that sends message to slack
  )

(defn not-found []
  [:div
   [:h1 {:class "info-warning"} "Page Not Found"]
   [:p "There's no requested page. "]
   (link-to {:class "btn btn-primary"} "/" "Take me to Home")])
