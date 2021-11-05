import java.io.*;
import java.util.*;

public class BfsCostGraph {
    static int graph[][];
    static int pointnum;
    static int start;
    static int end;

    /**
     * opentable中的元素
     */
    static class OpenElement implements Cloneable{
        int pointindex;
        int fatherindex;
        int cost;

        public OpenElement(int pointindex, int fatherindex, int cost) {
            this.pointindex = pointindex;
            this.fatherindex = fatherindex;
            this.cost = cost;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    /**
     * closetable中的元素
     */
    static class CloseElement{
        int pointindex;
        int fatherindex;

        public CloseElement(int pointindex, int fatherindex) {
            this.pointindex = pointindex;
            this.fatherindex = fatherindex;
        }
    }

    /**
     * 主函数
     * @param args
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        System.out.println("Input the pointnum：");
        Scanner sc=new Scanner(System.in);
        pointnum=sc.nextInt();
        System.out.println("Graph：");
        graph=createGraph(pointnum);
        System.out.println("Input start and end index：");
        start=sc.nextInt();
        end=sc.nextInt();
        bfsCost(graph,start,end);
    }

    /**
     * 根据输入节点数量随机生成图像邻接矩阵
     * @param pointnum
     * @return
     * @throws IOException
     */
    public static int[][] createGraph(int pointnum) throws IOException {
        int[][]graph=new int[pointnum+1][pointnum+1];
        File file= new File("resources/graph.txt");
        FileWriter out= new FileWriter(file);
        Random r=new Random();
        for(int i=1;i<=pointnum;i++){
            for(int j=1;j<=pointnum;j++){
                if(j>i){
                    graph[i][j]=r.nextInt(10);
                    out.write(graph[i][j]+" ");
                    System.out.print(graph[i][j] + " ");
                }
                if(j==i){
                    graph[i][j]=0;
                    out.write(graph[i][j]+" ");
                    System.out.print(graph[i][j] + " ");
                }
                if(j<i){
                    graph[i][j]=0;
                    out.write(graph[i][j]+" ");
                    System.out.print(graph[i][j] + " ");
                }
            }
            out.write("\r\n");
            System.out.println();
        }
        out.close();
        return graph;
    }

    /**
     *Bfs搜索过程
     * @param graph 邻接矩阵
     * @param start 开始结点
     * @param end   结束结点
     * @throws CloneNotSupportedException
     */
    public static void bfsCost(int [][]graph,int start,int end) throws CloneNotSupportedException {
        LinkedList<OpenElement> opentable=new LinkedList<OpenElement>();
        LinkedList<OpenElement> preopentable=new LinkedList<OpenElement>();
        LinkedList<CloseElement> closetable= new LinkedList<CloseElement>();
        opentable.add(new OpenElement(start,0,0));
        /**
         *当Open表不为空时，从中找出cost最小的openelement（标记为top）放入close表中：
         * 如果top序号为end则结束，否则判断是否可拓展
         * 若可拓展则将拓展节点更新cost并加入opentable，不可拓展则continue
         */
        while(!opentable.isEmpty()){
            Collections.sort(opentable,new OpenElementComparator());
            preopentable=cloneOpenTable(opentable);
            OpenElement top= opentable.removeFirst();

            closetable.addLast(new CloseElement(top.pointindex,top.fatherindex));
            if(top.pointindex==end){
                printPath(graph,closetable,start,end);
                break;
            }
            if(canExtend(graph,top.pointindex)){
                for(int i=1;i<=graph[0].length-1;i++){
                    if(graph[top.pointindex][i]!=0){
                        OpenElement extendOpenNode=new OpenElement(i,top.pointindex,
                                Objects.requireNonNull(findOpenElementByIdx(preopentable, top.pointindex)).cost+graph[top.pointindex][i]);
                        opentable.addLast(extendOpenNode);
                    }
                }
            }
            else {
                continue;
            }
        }
    }

    /**
     * 实现容器中的实例排序Comparator
     */
    public static class OpenElementComparator implements Comparator<OpenElement>{
        @Override
        public int compare(OpenElement o1, OpenElement o2) {
            return (o1.cost-o2.cost);
        }
    }

    /**
     * 深度拷贝opentable
     * @param opentable
     * @return
     * @throws CloneNotSupportedException
     */
    public static LinkedList<OpenElement> cloneOpenTable(LinkedList<OpenElement>opentable) throws CloneNotSupportedException {
        LinkedList<OpenElement> result=new LinkedList<>();
        for(OpenElement openelement:opentable){
            result.add((OpenElement)openelement.clone());
        }
        return result;
    }

    /**
     * 通过序号idx查找closetable中的元素
     * @param table
     * @param pointidx
     * @return
     */
    public static CloseElement findCloseElementByIdx(LinkedList<CloseElement> table,int pointidx){
       Iterator<CloseElement> iter=table.iterator();
       while (iter.hasNext()){
           CloseElement itnext=iter.next();
           if(itnext.pointindex==pointidx){
               return itnext;
           }
       }
       return null;
    }

    /**
     * 通过idx查找opentable中的元素
     * @param table
     * @param pointidx
     * @return
     */
    public static OpenElement findOpenElementByIdx(LinkedList<OpenElement> table,int pointidx){
        Iterator<OpenElement> iter=table.iterator();
        while (iter.hasNext()){
            OpenElement itnext=iter.next();
            if(itnext.pointindex==pointidx){
                return itnext;
            }
        }
        return null;
    }

    /**
     * 判断结点是否可拓展
     * @param graph 邻接矩阵图像
     * @param pointnum 结点序号
     * @return
     */
    public static boolean canExtend(int graph[][],int pointnum){
        for(int i=1;i<=graph[0].length-1;i++){
            if(graph[pointnum][i]!=0){
                return true;
            }
        }
        return false;
    }

    /**
     * 打印路径，从终止结点到开始结点，入栈再弹栈
     * @param graph
     * @param closetable
     * @param start
     * @param end
     */
    public static void printPath(int graph[][],LinkedList<CloseElement> closetable,int start,int end){
        Stack<CloseElement> pathStack=new Stack<CloseElement>();
        CloseElement closeNode=findCloseElementByIdx(closetable,end);
        while(closeNode.fatherindex!=0){
            pathStack.push(closeNode);
            closeNode=findCloseElementByIdx(closetable,closeNode.fatherindex);
        }
        pathStack.push(closeNode);

        System.out.print(closeNode.pointindex);
        pathStack.pop();
        int cost=0;
        int preIdx=start;
        int nextIdx=start;
        while (!pathStack.empty()){
            CloseElement popNode=pathStack.pop();
            nextIdx=popNode.pointindex;
            cost+=graph[preIdx][nextIdx];
            System.out.print("->"+nextIdx);
            preIdx=nextIdx;
        }
        System.out.println();
        System.out.println("The shortest cost is:"+cost);
    }
}
