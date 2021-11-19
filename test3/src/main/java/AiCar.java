import java.util.Stack;

public class AiCar {
    public static void main(String[] args)
    {
        int[][] maps = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }
        };
        MapInfo info=new MapInfo(maps,maps[0].length, maps.length,new Node(0,maps.length-1), new Node(4, 5));
        System.out.println("Map is: ");
        printMap(maps);
        Astar astar=new Astar();
        astar.start(info);
        int[][] finalMaps=info.getMaps();
        System.out.println("The routine of the car is: ");
        printMap(finalMaps);

        Stack<Node> path = astar.getPath();

        while (!path.isEmpty())
        {
            Node top1=path.pop();
            if(path.isEmpty())
            {
                break;
            }
            Node top2=path.peek();
            int speed=0;
            speed=FuzzySet.judgeSpeed(maps,top1,top2);
            System.out.println("The speed at ("+top1.coord.x+","+top1.coord.y+") is "+speed+" block/s");
        }
    }

    /**
     * 打印地图
     */
    public static void printMap(int[][] maps)
    {
        System.out.println("________________________________");
        for (int i = 0; i < maps.length; i++)
        {
            System.out.print('|');
            for (int j = 0; j < maps[i].length; j++)
            {
                if (maps[i][j]==0){
                    System.out.print(" ");
                }
                if (maps[i][j]==1){
                    System.out.print('O');
                }
                if (maps[i][j]==2){
                    System.out.print('*');
                }
                System.out.print(" ");
            }
            System.out.println('|');
        }
        System.out.println("\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014");
    }
}
