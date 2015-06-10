(ns dockerhub-webhook-listener.handler
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.java.shell :refer [sh]]
            [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.util.response :as resp]))

(defn ip->int [ip-str]
  (->> (str/split ip-str #"\.")
       (reverse)
       (map-indexed (fn [i x]
                      (-> (Integer. x)
                          (bit-shift-left (* 8 i)))))
       (reduce bit-or)))

(def dockerhub-low (ip->int "162.242.195.64"))
(def dockerhub-high (ip->int "162.242.195.127"))

(defn from-dockerhub? [ip-str]
  (<= dockerhub-low (ip->int ip-str) dockerhub-high))

(defn token-valid? [token]
  (= (System/getenv "DOCKERHUB_TOKEN") token))

(defn req-valid? [req]
  (and (from-dockerhub? (:remote-addr req))
       (token-valid? (get-in req [:params "token"]))))

(defn deploy []
  (->> (sh "rsc/deploy.sh")
       (str)))

(defroutes handler
  (POST "/" req (if (req-valid? req) (deploy) "Invalid request."))
  (route/not-found "Endpoint not found."))
