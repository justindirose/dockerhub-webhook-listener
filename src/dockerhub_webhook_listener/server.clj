(ns dockerhub-webhook-listener.server
  (:require [ring.middleware.params :refer [wrap-params]]
            [org.httpkit.server :refer [run-server]]
            [dockerhub-webhook-listener.handler :as h])
  (:gen-class))

(defn run [handler & [port]]
  (defonce ^:private server
    (let [port (Integer. (or port 8080))]
      (print "Starting web server on port " port ".\n")
      (run-server handler {:port port})))
  server)

(def handler (-> #'h/handler wrap-params))

(defn -main [& [port]]
  (run handler port))
