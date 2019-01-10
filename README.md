# hazelcast-testdrive

[![Build Status](https://travis-ci.org/oswaldobapvicjr/hazelcast-testdrive.svg?branch=master)](https://travis-ci.org/oswaldobapvicjr/hazelcast-testdrive)

A simple project for test-drive of Hazelcast basic features.

> **Disclaimer**: This is just a simple project with examples on how Hazelcast can be used to maintain some distributed data structures across a simple cluster. The source code herein is not (yet) production-ready.

---

## Getting started

Check out the classes in the `net.obvj.gettingstarted` package to get to know the basics of **Hazelcast** and **Hazelcast Client**.

> **Runnable classes:** `net.obvj.gettingstarted.GettingStarted` and `net.obvj.gettingstarted.GettingStatedClient`.  

## Task Dispatcher and Task Worker

The source code inside the `net.obvj.tasks` package implements simple components that dispatch and receive several instances of a dummy task `MyTask` around the cluster, with a dummy, persistent backup of tasks still in the distributed map to be processed. 

> **Runnable classes:** `net.obvj.tasks.TaskDispatcher` and `net.obvj.tasks.TaskWorker`.

Start as many processes of `TaskDispatcher` and `TaskWorker` as you want to test-drive Hazelcast functionalities.
