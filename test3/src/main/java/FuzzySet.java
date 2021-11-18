import Jama.Matrix;

public class FuzzySet {
    /**
     *  距离远——车速快的关系矩阵
     * 行为距离障碍距离，列为速度
     * 行从上往下距离越来越远，列从左往右速度越来越快
     */
    static double [][] DIS_SPEED= new double[][]{
            {0.9,0.4,0.2,0.2,0.1},
            {0.2,0.9,0.2,0.2,0.15},
            {0.2,0.1,0.8,0.2,0.1},
            {0.2,0.1,0.25,0.95,0.1},
            {0.2,0.1,0.25,0.25,1},
    };
    /**
     * 曲率小——车速快的关系矩阵
     * 行为转弯角度，列为速度
     * 行从上往下距角度越来越大，列从左往右速度越来越快
     */
    static double [][] CUR_SPEED= new double[][]{
            {0.1,0.4,0.2,0.2,0.9},
            {0.2,0.2,0.2,0.9,0.15},
            {0.2,0.1,0.8,0.2,0.1},
            {0.2,0.95,0.25,0.1,0.1},
            {1,0.1,0.25,0.25,0.2},
    };

    double [][] MIX_SPEED= mixRelation(DIS_SPEED,CUR_SPEED);

    /**
     * 联合模糊矩阵
     * @param disSpeed
     * @param curSpeed
     * @return
     */
    private double [][] mixRelation(double[][] disSpeed,double[][] curSpeed){
        double[][]result =new double[disSpeed.length][disSpeed[0].length];
        for(int i=0;i<disSpeed.length;i++)
        {
            for (int j=0;j<disSpeed[0].length;j++)
            {
                result[i][j]=Math.max(disSpeed[i][j],curSpeed[i][j]);
            }
        }
        return result;
    }

    /**
     * 最近障碍物距离
     * @param maps
     * @param node
     * @return
     */
    static int nearsetBar(int [][]maps, Node node)
    {
        Coord coord= node.coord;
        int circle=1;
        int circleX=coord.x;
        int circleY=coord.y;
        while (true)
        {
            for(int i = Math.max(0,circleX-circle);i<=Math.min(maps[0].length-1,circleX+circle);i++)
            {
                for(int j=Math.max(0,circleY-circle);j<=Math.min(maps.length-1,circleY+circle);j++){
                    if(maps[j][i]==Astar.BAR) {
                        return circle;
                    }
                }
            }
            circle++;
        }
    }

    /**
     * 距离障碍物远近的输入条件模糊向量
     * @param circle
     * @return
     */
    static Matrix getDisVector (int circle){
        double [][]result={{0.1,0.1,0.1,0.1,0.1}};
        for(int i=0;i<5;i++)
        {
            if (circle==i)
            {
                result[0][i]=0.9;
            }
        }
        return new Matrix(result);
    }

    /**
     * 车辆行驶角度大小的输入条件模糊向量
     * @param a
     * @param b
     * @return
     */
    static Matrix getCurveVector(Coord a,Coord b)
    {
        double [][]result={{0.1,0.1,0.1,0.1,0.1}};
        //成45度
        if(a.x!=b.x&&a.y!=b.y)
        {
            result[0][2]=0.9;
        }
        //成0度
        else
        {
            result[0][0]=0.9;
        }
        return new Matrix(result);
    }

}
