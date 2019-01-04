package net.obvj.tasks.listeners;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

import net.obvj.tasks.api.Task;

public class TaskQueueItemListener implements ItemListener<Task>
{

    public void itemAdded(ItemEvent<Task> event)
    {
        System.out.println("          [TaskQueueItemListener] Task " + event.getItem().getId()
                + " submitted to queue by " + event.getMember().toString());
    }

    public void itemRemoved(ItemEvent<Task> event)
    {
        System.out.println("          [TaskQueueItemListener] Task " + event.getItem().getId()
                + " removed from queue by " + event.getMember().toString());
    }

}
