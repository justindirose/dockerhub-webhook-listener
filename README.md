# dockerhub-webhook-listener
Simple Docker Hub webhook endpoint that runs a deploy script upon request

## Usage

This app consists of an http-kit server that listens for incoming requests on port 8080.

It checks:

1. That the request came from Docker Hub servers between ```162.242.195.64``` and ```162.242.195.127```
2. The ```token``` query parameter in the request matches the ```DOCKERHUB_TOKEN``` environment variable

If these checks pass, it executes a shell script in the container at /dockerhub-webhook-listener/rsc/deploy.sh (if available)

Docker is included in the image, so if you mount the Docker socket when running the container, you will have access to the host Docker daemon & can issue Docker commands from within deploy.sh.

Example showing shell script & docker socket mount:
```
docker pull zoondka/dockerhub-webhook-listener
docker run -d -p 8080:8080 -v /path/to/dockerhub-webhook-listener/rsc:/dockerhub-webhook-listener/rsc -v /var/run/docker.sock:/var/run/docker.sock --name dockerhub-webhook-listener zoondka/dockerhub-webhook-listener
```
