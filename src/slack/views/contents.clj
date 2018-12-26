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

(defn slack []
  [:div {:class "well-large"}
   [:form {:role "form" :method "post"}
    [:div {:class "form-group"}
      (label {:class "control-label"} "slack-Hook" "Slack Hook")
      (text-field {:class "span7" :placeholder "slack hook" :ng-model "hook" :name "hook"} "hook")
      (label {:class "control-label"} "slack-field" "Slack Message")
      (text-area {:class "span11" :placeholder "your message" :name "text" :rows "10" :cols "50"} "message")
      (submit-button {:class "btn-primary" :style "display:block"} "submit")]
      ]]
  )

(defn not-found []
  [:div
   [:h1 {:class "info-warning"} "Page Not Found"]
   [:p "There's no requested page. "]
   (link-to {:class "btn btn-primary"} "/" "Take me to Home")])
