package net.obvj.tasks.storage.mock;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.LineInputStream;

import net.obvj.tasks.MyTask;
import net.obvj.tasks.api.Task;
import net.obvj.tasks.storage.TasksQueueStore;

public final class TasksDBDummy
{

    private static final String tasksFile = "data/tasks.data";
    private Map<Long, Task> tasksMap = new TreeMap<>();
    private static TasksDBDummy instance = new TasksDBDummy();

    public static TasksDBDummy getInstance()
    {
        return instance;
    }

    public synchronized Set<Long> loadAllKeys()
    {
        loadAllFromFile();
        return this.tasksMap.keySet();
    }

    public synchronized Map<Long, Task> loadAll(Collection<Long> keys)
    {
        loadAllFromFile();
        Map<Long, Task> tasksMap = new TreeMap<>();
        for (Long key : keys)
        {
            tasksMap.put(key, this.tasksMap.get(key));
        }
        return tasksMap;
    }

    public synchronized Task load(Long key)
    {
        loadAllFromFile();
        return this.tasksMap.get(key);
    }

    public synchronized void store(Long key, Task task)
    {
        loadAllFromFile();
        this.tasksMap.put(key, task);
        writeAllToFile();
    }

    public synchronized void storeAll(Map<Long, Task> map)
    {
        loadAllFromFile();
        this.tasksMap.putAll(map);
        writeAllToFile();
    }

    public synchronized void delete(Long key)
    {
        loadAllFromFile();
        this.tasksMap.remove(key);
        writeAllToFile();
    }

    public synchronized void deleteAll(Collection<Long> keys)
    {
        loadAllFromFile();
        for (Long key : keys)
        {
            tasksMap.remove(key);
        }
        writeAllToFile();
    }

    private synchronized void writeAllToFile()
    {
        File file = new File(tasksFile);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
        try (FileWriter writer = new FileWriter(file, false))
        {
            for (Entry<Long, Task> entry : tasksMap.entrySet())
            {
                writer.write(entry.getKey() + ": id=" + entry.getValue().getId() + "\n");
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private synchronized void loadAllFromFile()
    {
        tasksMap = new TreeMap<>();
        try (LineInputStream in = new LineInputStream(new FileInputStream(tasksFile)))
        {
            String line = "";
            while ((line = in.readLine()) != null)
            {
                String s[] = line.split(": id=");
                tasksMap.put(Long.parseLong(s[0].trim()), new MyTask(Long.parseLong(s[1].trim())));
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

    }

    public static void main(String[] args)
    {
        TasksQueueStore t = new TasksQueueStore();
        System.out.println(t.loadAllKeys());

        Collection<Long> keys = new ArrayList<>();
        Collections.addAll(keys, 1l, 2l);
        System.out.println(t.loadAll(keys));

        System.out.println(t.load(6l));

        t.store(7l, new MyTask(77));

        Map<Long, Task> storeTasks = new TreeMap<>();
        storeTasks.put(8l, new MyTask(80));
        storeTasks.put(9l, new MyTask(90));
        t.storeAll(storeTasks);

        t.delete(7l);

        Collection<Long> keys2 = new ArrayList<>();
        Collections.addAll(keys2, 8l, 9l);
        t.deleteAll(keys2);
    }

}
