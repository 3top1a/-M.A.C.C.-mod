package _3top1a.AutoMaCraft;

public class Utils {
    public static void PrintError(Exception e) throws Exception
    {
        System.out.println("Error!");
        System.out.println( e.getMessage() );
        //System.out.println( e.getCause() );
        e.printStackTrace();

    }
}
