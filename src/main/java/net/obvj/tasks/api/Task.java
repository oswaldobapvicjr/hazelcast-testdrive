package net.obvj.tasks.api;

import com.hazelcast.nio.serialization.DataSerializable;

public interface Task extends Runnable, DataSerializable
{
    long getId();
}
