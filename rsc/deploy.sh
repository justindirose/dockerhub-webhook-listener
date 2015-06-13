#!/bin/bash

/usr/bin/docker pull zoondka/zoondka-maps
/usr/bin/docker stop zoondka-maps
/usr/bin/docker rm zoondka-maps
/usr/bin/docker run -d -p 8090:8090 --name zoondka-maps -t zoondka/zoondka-maps

exit 0
