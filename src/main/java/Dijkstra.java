public class Dijkstra {
    public static void main(String[] arg) {
        Graph graph = new Graph();
        //graph.graphPrint();
        int[] ans = graph.Dijkstra(3);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(i + ": " + ans[i]);
        }
    }
}


class Graph {
    private int[][] matrix;
    private int vertexNum = 7;

    Graph() {
        matrix = new int[vertexNum][vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            for (int j = i; j < vertexNum; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
                if (i == j) {
                    matrix[i][j] = 0;
                }
            }
        }
        matrix[0][1] = 12;
        matrix[0][5] = 16;
        matrix[0][6] = 14;
        matrix[1][5] = 7;
        matrix[1][2] = 10;
        matrix[2][3] = 3;
        matrix[2][4] = 5;
        matrix[2][5] = 6;
        matrix[3][4] = 4;
        matrix[4][5] = 2;
        matrix[4][6] = 8;
        matrix[5][6] = 9;
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < i; j++) {
                matrix[i][j] = matrix[j][i];
            }
        }
    }

    //vertex number from 0 to N-1
    Graph(int[][] times, int N) {
        vertexNum = N;
        matrix = new int[vertexNum][vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                matrix[i][j] = Integer.MAX_VALUE;
                if (i == j) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < times.length; i++) {
            matrix[times[i][0]][times[i][1]] = times[i][2];
        }
    }

    public int[] Dijkstra(int srcPoint) {
        int[] shortestDist = new int[vertexNum];
        int[] reachableDist = new int[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            reachableDist[i] = Integer.MAX_VALUE;
            shortestDist[i] = Integer.MAX_VALUE;
        }
        shortestDist[srcPoint] = 0;
        int temp = srcPoint;

        for (int i = 0; i < vertexNum - 1; i++) {
            for (int j = 0; j < vertexNum; j++) {
                if (shortestDist[j] == Integer.MAX_VALUE && matrix[temp][j] != Integer.MAX_VALUE) {
                    if (reachableDist[j] > shortestDist[temp] + matrix[temp][j])
                        reachableDist[j] = shortestDist[temp] + matrix[temp][j];
                }

            }

            //can be optimized by minimum heap(insert from the end, pop from the begin, every parent < it's child)
            int min = Integer.MAX_VALUE;
            for (int k = 0; k < vertexNum; k++) {
                if (shortestDist[k] == Integer.MAX_VALUE && reachableDist[k] < min) {
                    temp = k;
                    min = reachableDist[k];
                }
            }
            shortestDist[temp] = reachableDist[temp];
        }


        return shortestDist;
    }

    public void graphPrint() {
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                System.out.print(matrix[i][j] + " \t");
            }
            System.out.println();
        }
    }
}
