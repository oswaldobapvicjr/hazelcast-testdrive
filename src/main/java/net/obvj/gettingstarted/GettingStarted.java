package net.obvj.gettingstarted;

import java.util.Map;
import java.util.Queue;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class GettingStarted
{

    public static void main(String[] args)
    {

        Config config = new Config();
        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);

        Map<Integer, String> mapCustomers = hazelcast.getMap("customers");
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        // Iterating through Map
        System.out.println("Map size: " + mapCustomers.size());
        for (Map.Entry<Integer, String> entry : mapCustomers.entrySet())
        {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        Queue<String> queueCustomers = hazelcast.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");

        // Testing queue
        System.out.println();
        System.out.println("Queue size: " + queueCustomers.size());
        System.out.println("Poll first customer: " + queueCustomers.poll());
        System.out.println("Peek next customer: " + queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());
    }

}
