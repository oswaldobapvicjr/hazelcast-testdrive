package net.obvj.gettingstarted;

import java.util.Map.Entry;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;

public class GettingStartedClient
{

    public static void main(String[] args)
    {

        HazelcastInstance client = HazelcastClient.newHazelcastClient();

        IMap<Integer, String> map = client.getMap("customers");

        // Iterating through shared Map
        System.out.println("Customers map size:" + map.size());
        for (Entry<Integer, String> entry : map.entrySet())
        {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

        IQueue<String> queue = client.getQueue("customers");

        // Testing shared Queue
        System.out.println();
        System.out.println(queue.peek());

    }
}
