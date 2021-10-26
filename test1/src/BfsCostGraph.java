import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BfsCostGraph {

    static int pointnum=50;
    static int edgenum;

    static int[][]graph= new int[pointnum][pointnum];

    public static void main(String[] args) {
        read();
    }

    static void read() {

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader raf = null;

        try {
            fis = new FileInputStream("resources/graph.txt");
            isr = new InputStreamReader(fis);
            raf = new BufferedReader(isr);

            String s = null;

            while((s = raf.readLine()) != null) {
                String[] spacesplit = s.trim().split(" ");
                System.out.println(spacesplit.length);
                int edgea=Integer.parseInt(spacesplit[0]);
                int edgeb=Integer.parseInt(spacesplit[1]);
                int cost=Integer.parseInt(spacesplit[2]);
                graph[edgea][edgeb]=cost;
                graph[edgeb][edgea]=cost;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
