# dockerhub-webhook-listener
Simple Docker Hub webhook endpoint that runs a deploy script upon request

## Usage
```bash
docker pull zoondka/dockerhub-webhook-listener
docker run -d -p 8080:8080 -v /path/to/dockerhub-webhook-listener/rsc:/dockerhub-webhook-listener/rsc -v /var/run/docker.sock:/var/run/docker.sock --name dockerhub-webhook-listener zoondka/dockerhub-webhook-listener
```
