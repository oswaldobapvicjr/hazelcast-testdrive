package net.obvj.tasks;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;

import net.obvj.tasks.api.Task;

public class MyTask implements Task
{
    private long id;

    public MyTask()
    {
    }

    public MyTask(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return this.id;
    }

    /*
     * NOTE: In a real solution, do not forget to make this operation atomic
     */
    public void run()
    {
        if (id == 0)
        {
            throw new IllegalStateException("for MyTask.id not set");
        }
        System.out.print("[MyTask] Task #" + id + ": run() -> [ ");

        try
        {
            for (int i = 0; i < 10; i++)
            {
                Thread.sleep(1000);
                System.out.print("# ");
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("[MyTask] Task #" + id + ": I was interrupted");
        }

        System.out.println("] - Done");
    }

    public void readData(ObjectDataInput out) throws IOException
    {
        id = out.readLong();
    }

    public void writeData(ObjectDataOutput out) throws IOException
    {
        out.writeLong(id);
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String toString()
    {
        return "MyTask[id=" + id + "]";
    }

    public static void main(String[] args)
    {
        new MyTask(1).run();
    }

}
