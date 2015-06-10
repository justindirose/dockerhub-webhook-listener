(def project {:name "dockerhub-webhook-listener" :version "0.1.0"})

(set-env!
  :source-paths #{"src"}
  :target-path  "target"
  :dependencies '[[org.clojure/clojure "1.6.0"]
                  [ring "1.4.0-RC1"]
                  [compojure "1.3.4"]
                  [http-kit "2.1.19"]])

(require
  '[dockerhub-webhook-listener.server       :as server]
  '[ring.middleware.reload                  :as reload]
  '[ring.middleware.file                    :as file]
  '[ring.middleware.file-info               :as file-info])


(defn dev-handler []
  (-> server/handler (reload/wrap-reload)
    (file/wrap-file "target")
    (file-info/wrap-file-info)))

(deftask dev
  "Start internal httpkit server for development."
  []
  (with-post-wrap fileset (server/run (dev-handler))))

(deftask prod
  "Build application uberjar with http-kit main."
  []
  (comp (aot :all true)
        (uber)
        (jar :file (str (:name project) ".jar")
             :main 'dockerhub-webhook-listener.server)))

(deftask install-local
  "Install to local Maven repository."
  []
  (comp (aot :all true)
        (pom :project (symbol (:name project))
             :version (:version project))
        (jar)
        (install)))
