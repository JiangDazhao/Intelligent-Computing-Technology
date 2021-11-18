import java.util.*;

/**
 * A星算法
 */
public class Astar {
    // 障碍值
    public final static int BAR = 1;
    // 绘制路径
    public final static int PATH = 2;
    // 横竖移动代价
    public final static int DIRECT_VALUE = 10;
    // 斜移动代价
    public final static int OBLIQUE_VALUE = 14;

    // 优先队列(升序)
    Queue<Node> openList = new PriorityQueue<Node>();
    List<Node> closeList = new ArrayList<Node>();

    public Stack<Node> path = new Stack<Node>();

    public Stack<Node> getPath() {
        return path;
    }

    /**
     * 入口函数
     */
    public void start(MapInfo mapInfo)
    {
        if (mapInfo==null) {
            return;
        }
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.start);
        moveNodes(mapInfo);
    }

    /**
     * 从入口开始移动
     */
    private void moveNodes(MapInfo mapInfo){
        while (!openList.isEmpty()){
            Node current = openList.poll();
            closeList.add(current);
            addNeighborNodeInOpen(mapInfo,current);
            if (isCoordInClose(mapInfo.end.coord))
            {
                drawPath(mapInfo.maps, mapInfo.end);
                break;
            }
        }
    }

    /**
     * 在二维数组中绘制路径
     */
    private void drawPath(int[][] maps, Node end)
    {
        int result = end.G;
        if(end==null||maps==null) {
            return;
        }
        this.path.push(end);
        while (end != null)
        {
            Coord c = end.coord;
            maps[c.y][c.x] = PATH;
            end = end.parent;
            if(end != null)
            {
                this.path.push(end);
            }
        }
        System.out.println("行车总代价为："+result);
    }

    /**
     * 添加所有邻结点到open表,addNeighborNodeInOpen内部判断各种情况
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current)
    {
        int x = current.coord.x;
        int y = current.coord.y;
        // 左
        addNeighborNodeInOpen(mapInfo,current, x - 1, y, DIRECT_VALUE);
        // 上
        addNeighborNodeInOpen(mapInfo,current, x, y - 1, DIRECT_VALUE);
        // 右
        addNeighborNodeInOpen(mapInfo,current, x + 1, y, DIRECT_VALUE);
        // 下
        addNeighborNodeInOpen(mapInfo,current, x, y + 1, DIRECT_VALUE);
        // 左上
        addNeighborNodeInOpen(mapInfo,current, x - 1, y - 1, OBLIQUE_VALUE);
        // 右上
        addNeighborNodeInOpen(mapInfo,current, x + 1, y - 1, OBLIQUE_VALUE);
        // 右下
        addNeighborNodeInOpen(mapInfo,current, x + 1, y + 1, OBLIQUE_VALUE);
        // 左下
        addNeighborNodeInOpen(mapInfo,current, x - 1, y + 1, OBLIQUE_VALUE);
    }

    /**
     * 添加邻节点到open表中，并更新
     * @param mapInfo
     * @param current
     * @param newx
     * @param newy
     * @param value
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int newx, int newy, int value){
        //如果是合适的open邻节点
        if(canAddNodeToOpen(mapInfo,newx,newy))
        {
            Node end=mapInfo.end;
            Coord newCoord = new Coord(newx, newy);
            //邻节点的G值
            int newG = current.G + value;
            //如果openlist没有该邻节点
            Node child = findNodeInOpen(newCoord);
            if (child == null)
            {
                // 计算邻节点的H值
                int newH=calcH(end.coord,newCoord);
                //和终止节点一致直接赋值
                if(isEndNode(end.coord,newCoord))
                {
                    child=end;
                    child.parent=current;
                    child.G=newG;
                    child.H=newH;
                }
                else
                {
                    child = new Node(newCoord, current, newG, newH);
                }
                openList.add(child);
            }
            // openlist有该节点且G值大,更新
            else if (child.G> newG)
            {
                child.G=newG;
                child.parent=current;
            }
        }
    }

    /**
     * 从Open列表中查找结点
     */
    private Node findNodeInOpen(Coord coord)
    {
        if (coord == null || openList.isEmpty()) {
            return null;
        }
        for (Node node : openList)
        {
            if (node.coord.equals(coord))
            {
                return node;
            }
        }
        return null;
    }

    /**
     * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
     */
    private int calcH(Coord end,Coord coord)
    {
        return (Math.abs(end.x - coord.x) + Math.abs(end.y - coord.y)) * DIRECT_VALUE;
    }

    /**
     * 判断结点是否是最终结点
     */
    private boolean isEndNode(Coord end,Coord coord)
    {
        return coord != null && end.equals(coord);
    }

    /**
     * 判断结点能否放入Open列表
     */
    private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y)
    {
        // 是否在地图中
        if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.height) {
            return false;
        }
        // 判断是否是不可通过的结点
        if (mapInfo.maps[y][x] == BAR) {
            return false;
        }
        // 判断结点是否存在close表
        if (isCoordInClose(x, y)) {
            return false;
        }

        return true;
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(Coord coord)
    {
        return coord!=null&&isCoordInClose(coord.x, coord.y);
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(int x, int y)
    {
        if (closeList.isEmpty()) {
            return false;
        }
        for (Node node : closeList)
        {
            if (node.coord.x == x && node.coord.y == y)
            {
                return true;
            }
        }
        return false;
    }
}
