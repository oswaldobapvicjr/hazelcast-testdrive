package net.obvj.tasks;

import java.util.concurrent.TimeUnit;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import net.obvj.tasks.api.Task;

public class TaskWorker
{

    public static void main(String[] args)
    {

        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance();

        // Our distributed tasks queue
        IQueue<Task> taskQueue = hazelcast.getQueue("tasks");

        try
        {
            while (true)
            {
                Task task;
                task = taskQueue.poll(5, TimeUnit.SECONDS);
                if (task != null)
                {
                    task.run();
                }
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("[main] TaskWorker interrupted");
        }

    }
}
