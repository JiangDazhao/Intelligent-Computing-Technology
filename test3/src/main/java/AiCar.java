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
        System.out.println("地图为：");
        printMap(maps);
        Astar astar=new Astar();
        astar.start(info);
        int[][] finalMaps=info.getMaps();
        System.out.println("小车的行车轨迹为：");
        printMap(finalMaps);

        Stack<Node> path = astar.getPath();

        while (!path.isEmpty())
        {
            Node top=path.pop();
            System.out.println(top.coord.x+" "+top.coord.y);
            System.out.println(FuzzySet.nearsetBar(maps,top));
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
        System.out.println("————————————————————————————————");
    }
}
