package b.a.a;

import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public abstract class Application implements Runnable
{
    public static void main(String[] args) {
        try 
        {
            System.out.println("App started");
            Thread.sleep(10000);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
}

