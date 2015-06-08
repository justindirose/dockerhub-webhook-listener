(ns dockerhub-webhook-listener.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.util.response :as resp]))

(defroutes handler
  (GET "/test" req (str req))
  (route/not-found "Endpoint not found."))
