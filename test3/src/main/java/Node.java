/**
 * 封装节点类，同时实现Comparable接口方便优先队列排序
 */
public class Node implements Comparable<Node>{
    public Coord coord;
    public Node parent;
    //cost from start to now
    public int G;
    //cost from now to dst
    public int H;

    public Node(int x, int y)
    {
        this.coord = new Coord(x, y);
    }

//    public Node(int G)
//    {
//        this.G=G;
//    }

    public Node(Coord coord, Node parent, int g, int h)
    {
        this.coord = coord;
        this.parent = parent;
        G = g;
        H = h;
    }



    /**
     * 升序排列
     */
    @Override
    public int compareTo(Node o) {
        if (o==null) {
            return -1;
        }
        if (this.G+this.H>o.G+o.H){
            return 1;
        }
        if (this.G+this.H<o.G+o.H){
            return -1;
        }
        return 0;
    }

//    @Override
//    /**
//     * 升序排列
//     */
//    public int compareTo(Node o) {
//        if (G>o.G){
//            return 1;
//        }
//        else if (G<o.G){
//            return -1;
//        }
//        return 0;
//    }

}
