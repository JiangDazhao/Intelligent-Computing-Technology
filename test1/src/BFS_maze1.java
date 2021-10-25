import java.util.*;

public class BFS_maze1 {
    static class node{
        int x;
        int y;
        int step;
        public node(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.step = step;
        }
    }

    static int MAXV=999;
    static int n,m;
    static char[][] map= new char[MAXV][MAXV];
    static boolean[][] inqueue=new boolean[MAXV][MAXV];
    static int result=-1;
    static int[][]move={{-1,0},{1,0},{0,-1},{0,1}};

    static void bfs(int x,int y){
        Queue<node> q= new LinkedList<node>();
        q.add(new node(x,y,0));
        inqueue[x][y]=true;
        while (!q.isEmpty()){
            node temp=q.poll();

            if(map[temp.x][temp.y]=='T'){
                result=temp.step;
                return;
            }

            for(int i=0;i<4;i++){
                int nx=temp.x+move[i][0];
                int ny=temp.y+move[i][1];
                int step=temp.step+1;
                if(check(nx,ny)){
                    q.offer(new node(nx,ny,step));
                    inqueue[nx][ny]=true;
                }
            }
        }
    }



    static boolean check(int x,int y){
        if(x>=0&&x<n&&y>=0&&y<n&&!inqueue[x][y]&&map[x][y]!='#'){
            return true;
        }
        return false;
    }




    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n=sc.nextInt();
        m=sc.nextInt();
        int x=0,y=0;
        for(int i=0;i<n;i++){
            String s= sc.next();
            for(int j=0;j<s.length();j++){
                if(s.charAt(j)=='S'){
                    x=i;
                    y=j;
                }
            }
            map[i]=s.toCharArray();
        }
        bfs(x,y);
        System.out.println(result);
    }
}
