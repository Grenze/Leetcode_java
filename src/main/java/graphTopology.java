import java.util.Stack;

class graphTopology {
    public boolean topology = true;

    graphTopology(int vertexNum, int[][] edges) {
        node[] nodeList = new node[vertexNum];

        Stack<Integer> toVisitList = new Stack<Integer>();
        ;

        for (int i = 0; i < vertexNum; i++) {
            nodeList[i] = new node();
        }


        int pre, nex;
        for (int i = 0; i < edges.length; i++) {
            pre = edges[i][0];
            nex = edges[i][1];
            nodeList[pre].pointNode.push(nex);
            nodeList[nex].inDegree++;
        }

        for (int i = 0; i < vertexNum; i++) {
            if (nodeList[i].inDegree == 0) {
                toVisitList.push(i);
            }
        }

        int count = 0;
        int temp;
        while (!toVisitList.empty()) {
            temp = toVisitList.pop();
            count++;
            //System.out.println(temp);
            while (!nodeList[temp].pointNode.empty()) {
                int point = nodeList[temp].pointNode.pop();
                nodeList[point].inDegree--;
                if (nodeList[point].inDegree == 0) {
                    toVisitList.push(point);
                }
            }
        }
        if (count != vertexNum) {
            topology = false;
        }


    }


    private class node {
        Stack<Integer> pointNode = new Stack<Integer>();
        int inDegree = 0;
    }
}