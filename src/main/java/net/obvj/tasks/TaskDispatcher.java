package net.obvj.tasks;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import net.obvj.tasks.api.Task;

public class TaskDispatcher
{

    public static void main(String[] args)
    {

        // A Client instance is not cluster member.
        // Clients can be smart or dumb:
        // Dumb clients will connect to a random member in the cluster and send
        // requests to this member. This member then needs to send the request
        // to the correct member.
        HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient();

        // Our distributed tasks queue
        IQueue<Task> taskQueue = hazelcast.getQueue("tasks");

        // Item listener for this queue (doesn't work in client instances)
        // ItemListener<Task> taskQueueItemListener = new TaskQueueItemListener();
        // taskQueue.addItemListener(taskQueueItemListener, true);

        Scanner scanner = new Scanner(System.in);
        int i = 1;

        System.out.println("[main] Press <ENTER> to submit a task. Type 'exit' to quit.");

        String line;
        while ((line = scanner.nextLine()) != null)
        {
            try
            {
                if (line.equals("exit") || line.equals("quit"))
                {
                    break;
                }
                System.out.println("[main] Submitting task " + i + "...");

                boolean offered = taskQueue.offer(new MyTask(i), 2, TimeUnit.SECONDS);
                if (!offered)
                {
                    throw new TimeoutException();
                }
                System.out.println("[main] Task " + i++ + " submitted.");

            }
            catch (TimeoutException e)
            {
                System.out.println("[main] ERROR: Task NOT submitted: Tasks queue too busy.");
            }
            catch (InterruptedException e)
            {
                System.out.println("[main] TaskDispatcher interrupted.");
            }
        }
        scanner.close();
        System.out.println("[main] Dispatcher terminated.");
        System.exit(0);
    }
}
