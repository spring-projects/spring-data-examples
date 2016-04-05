# Spring Data Redis - Cluster Examples #

This project contains Redis 3 Cluster specific features of Spring Data Redis.

To run the code in this sample a running cluster environment is required. Please refer to the  [redis cluster-tutorial](http://redis.io/topics/cluster-tutorial) for detailed information or check the Cluster Setup section below. 

## Support for Cluster ##

Cluster Support uses the same building blocks as the non clustered counterpart. We use `application.properties` to point to an initial set of known cluster nodes which will be picked up by the auto configuration.

```properties
spring.redis.cluster.nodes[0]=127.0.0.1:30001
spring.redis.cluster.nodes[1]=127.0.0.1:30002
spring.redis.cluster.nodes[2]=127.0.0.1:30003
```

**INFORMATION:** The tests flush the db of all known instances during the JUnit _setup_ phase to allow inspecting data directly on the cluster nodes after a test is run.

## Cluster Setup ##

To quickly set up a cluster of 6 nodes (3 master | 3 slave) go to the `redis/utils/create-cluster` directory.


```bash
redis/utils/create-cluster $ ./create-cluster start
  Starting 30001
  Starting 30002
  Starting 30003
  Starting 30004
  Starting 30005
  Starting 30006
```

On first initialization cluster nodes need to form the cluster by joining and assigning slot allocations.
**INFO**: This has to be done only once.

```bash
redis/utils/create-cluster $ ./create-cluster create
  >>> Creating cluster
  >>> Performing hash slots allocation on 6 nodes...
  Using 3 masters:
    127.0.0.1:30001
    127.0.0.1:30002
    127.0.0.1:30003
  Adding replica 127.0.0.1:30004 to 127.0.0.1:30001
  Adding replica 127.0.0.1:30005 to 127.0.0.1:30002
  Adding replica 127.0.0.1:30006 to 127.0.0.1:30003

  M: 10696916f57e58c5edce34127b23ca7af1b669a0 127.0.0.1:30001
     slots:0-5460 (5461 slots) master
  M: 5b0e1b4cc87175326ba79d00ecfc6f5dbdb424a7 127.0.0.1:30002
     slots:5461-10922 (5462 slots) master
  M: 5f3e978fb40b1d9c910d904ea19a0494b78668aa 127.0.0.1:30003
     slots:10923-16383 (5461 slots) master
  S: d1717c418d03db93183ce2d791ba6f48be5cf028 127.0.0.1:30004
     replicates 10696916f57e58c5edce34127b23ca7af1b669a0
  S: c7dfcdb9cd1105e4251de51c4ade54de59bb063c 127.0.0.1:30005
     replicates 5b0e1b4cc87175326ba79d00ecfc6f5dbdb424a7
  S: 3219785a9145717f30648a27a2dd07359e9dd46f 127.0.0.1:30006
     replicates 5f3e978fb40b1d9c910d904ea19a0494b78668aa

  Can I set the above configuration? (type 'yes' to accept): yes

  [OK] All nodes agree about slots configuration.
       >>> Check for open slots...
       >>> Check slots coverage...
  [OK] All 16384 slots covered.
```

It is now possible to connect to the cluster using the `redis-cli`.

```bash
redis/src $ ./redis-cli -c -p 30001
  127.0.0.1:30001> cluster nodes

  106969... 127.0.0.1:30001 myself,master - 0 0 1 connected 0-5460
  5b0e1b... 127.0.0.1:30002 master - 0 1450765112345 2 connected 5461-10922
  5f3e97... 127.0.0.1:30003 master - 0 1450765112345 3 connected 10923-16383
  d1717c... 127.0.0.1:30004 slave 106969... 0 1450765112345 4 connected
  c7dfcd... 127.0.0.1:30005 slave 5b0e1b... 0 1450765113050 5 connected
  321978... 127.0.0.1:30006 slave 5f3e97... 0 1450765113050 6 connected
```

To shutdown the cluster use the `create-cluster stop` command.

```bash
redis/utils/create-cluster $ ./create-cluster stop
```
