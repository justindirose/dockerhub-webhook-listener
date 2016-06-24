(ns dockerhub-webhook-listener.handler
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.java.shell :refer [sh]]
            [compojure.core :refer [POST defroutes]]
            [compojure.route :as route]
            [ring.util.response :as resp]))

(defn token-valid? [token]
  (= (System/getenv "DOCKERHUB_TOKEN") token))

(defn req-valid? [req]
  (= token-valid? (get-in req [:params "token"])))

(defn deploy []
  (->> (sh "rsc/deploy.sh")
       (str)))

(defroutes handler
  (POST "/hubhook" req (if (req-valid? req) (deploy) (str "Invalid request. " req)))
  (route/not-found "Endpoint not found."))
