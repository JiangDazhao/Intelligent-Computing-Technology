import java.io.*;
import java.util.*;

public class BfsCostGraph {
    static int graph[][];
    static int pointnum;
    static int start;
    static int end;

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

    static class CloseElement{
        int pointindex;
        int fatherindex;

        public CloseElement(int pointindex, int fatherindex) {
            this.pointindex = pointindex;
            this.fatherindex = fatherindex;
        }
    }

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        System.out.println("输入图像节点数目：");
        Scanner sc=new Scanner(System.in);
        pointnum=sc.nextInt();
        System.out.println("生成的图像邻接矩阵为：");
        graph=createGraph(pointnum);
        System.out.println("输入起点和终点：");
        start=sc.nextInt();
        end=sc.nextInt();
        bfsCost(graph,start,end);
    }

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

    public static void bfsCost(int [][]graph,int start,int end) throws CloneNotSupportedException {
        LinkedList<OpenElement> opentable=new LinkedList<OpenElement>();
        LinkedList<OpenElement> preopentable=new LinkedList<OpenElement>();
        LinkedList<CloseElement> closetable= new LinkedList<CloseElement>();
        opentable.add(new OpenElement(start,0,0));
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

    public static class OpenElementComparator implements Comparator<OpenElement>{
        @Override
        public int compare(OpenElement o1, OpenElement o2) {
            return (o1.cost-o2.cost);
        }
    }

    public static LinkedList<OpenElement> cloneOpenTable(LinkedList<OpenElement>opentable) throws CloneNotSupportedException {
        LinkedList<OpenElement> result=new LinkedList<>();
        for(OpenElement openelement:opentable){
            result.add((OpenElement)openelement.clone());
        }
        return result;
    }

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

    public static boolean canExtend(int graph[][],int pointnum){
        for(int i=1;i<=graph[0].length-1;i++){
            if(graph[pointnum][i]!=0){
                return true;
            }
        }
        return false;
    }

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
        System.out.println("最短路径长度为:"+cost);
    }
}
