# Java External cache on Redis cluster with 0 downtime 
This is a demo application of using Redisson with Redis cluster with 0 application requests downtime on event of Redis cluster failover

# How to run

1. Build the JAR artefact e.g. with

    ```maven package```

2. Ensure docker, and docker-compose are installed on your system.

3. Change directory to ```deploy``` , which is including the docker-compose descriptor to startup Redis cluster nodes, link them up into a cluster, and build and start Java application on top of that with ```Dockerfile``` based on JAR produced at previous step
4. Issue requests to endpoints exposed by this Application, or use ```deploy/multiple_calls.sh``` script to simulate trivial infinite service calling loop

# What scenarios covered in behind

There is an attempt to show two particular scenarios for External cache use cases. Let's assume our app is doing some kind of online film services.

1. Perform any business check with ```/api/v1/check``` endpoint. Whatsoever it may be, the purpose of this is to produce check result and cache that for subsequent steps of business process. Check result is stored with randomly generated UUID for demo
2. Call and cache some external data source with ```/api/v1/getFilms```. This is to demonstrate benefits of shared external cache holding data from some data source, which is to be request optimized for any reason of RPS limits, cost penalties, or just redundancy 

Particularly for use case 1 it's handy to show application fault tolerance based on Redisson timeout and retry, with the event of any Redis single node go down. With this to happen Redis cluster looses it's coverage and it takes few seconds to come down to new cluster configuration. All that goes without errors at service layer. To try this scenario, start request flow with ```deploy/multiple_calls.sh``` and try to stop / start back up any single of Redis cluster nodes