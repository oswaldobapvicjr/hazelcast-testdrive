package net.obvj.tasks.storage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.hazelcast.core.QueueStore;

import net.obvj.tasks.api.Task;
import net.obvj.tasks.storage.mock.TasksDBDummy;

/*
 * HAZELCAST allows us to load and store distributed map entries from/to a
 * relational database or any other data storage. If a MapStore implementation
 * is provided, when get(key) is called, if the map entry doesn't exist
 * in-memory then HAZELCAST will call your loader implementation to load the
 * entry from the persistent storage
 *
 * NOTE: For the backing maps, the type of the key must always be Long
 */
public class TasksQueueStore implements QueueStore<Task>
{

    private static final boolean debug = false;

    TasksDBDummy db = TasksDBDummy.getInstance();

    /*
     * Constructor must not have any parameter since it's called by Hazelcast MapStore factory
     * via Reflection
     */
    public TasksQueueStore()
    {
    }

    @Override
    public Set<Long> loadAllKeys()
    {
        if (debug) System.out.println("[TasksMapStore] loadAllKeys() invoked");
        return db.loadAllKeys();
    }

    @Override
    public Map<Long, Task> loadAll(Collection<Long> keys)
    {
        if (debug) System.out.println("[TasksMapStore] loadAll() invoked for " + keys.size() + " keys");
        return db.loadAll(keys);
    }

    @Override
    public Task load(Long key)
    {
        if (debug) System.out.println("[TasksMapStore] load() invoked for key=" + key);
        return db.load(key);
    }

    @Override
    public void store(Long key, Task task)
    {
        if (debug) System.out.println("[TasksMapStore] store() invoked for task.id=" + task.getId());
        db.store(key, task);
    }

    @Override
    public void storeAll(Map<Long, Task> map)
    {
        if (debug) System.out.println("[TasksMapStore] storeAll() invoked");
        db.storeAll(map);
    }

    @Override
    public void delete(Long key)
    {
        if (debug) System.out.println("[TasksMapStore] delete() invoked for key=" + key);
        db.delete(key);
    }

    @Override
    public void deleteAll(Collection<Long> keys)
    {
        if (debug) System.out.println("[TasksMapStore] deleteAll() invoked for " + keys.size() + " keys");
        db.deleteAll(keys);
    }

}
