import java.io.IOException;
import java.util.logging.Filter;

public class main {
    public static void main(String args[]) throws IOException {

        if(args.length>0)
        {
            InputData input=new InputData(args);
            input.Process();
        }
        else
        {
            System.out.println("Choose one option:");
            System.out.println("  crowler");
            System.out.println("  extension");
            System.out.println("  limit");
            System.out.println("  search");
            System.out.println("  sitemap");
        }

    }
}