(ns slack.views.layout
  (:use [hiccup.page :only (html5 include-css include-js)])
  (:require [clj-http.client :as client]
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


(defn application [title & content]
  (html5 {:ng-app "myApp" :lang "en"}
         [:head
          [:title title]
          (include-css "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css")
          (include-js "http://code.angularjs.org/1.2.3/angular.min.js")
          (include-js "js/ui-bootstrap-tpls-0.7.0.min.js")
          (include-js "js/script.js")

          [:body
           [:div {:class "container"} content ]]])
  )
