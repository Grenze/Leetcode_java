
//divide into tree situations:
// a vertex with two in degree with a loop or not
// a loop
public class treeWithAnErrorBranch {
    treeNode[] nodeArray;
    int[] errLink = new int[2];
    int[] errLinkSec = new int[2];
    int[] errLinkThird = new int[2];

    treeWithAnErrorBranch(int[][] edges) {
        int nodeNum = edges.length + 1;
        nodeArray = new treeNode[nodeNum];

        for (int i = 1; i < nodeNum; i++) {
            nodeArray[i] = new treeNode(i);
        }
        int parent;
        int child;
        int flag = 0;
        for (int i = 0; i < nodeNum - 1; i++) {
            parent = edges[i][0];
            child = edges[i][1];
            if (find(parent) == find(child)) {
                errLinkThird[0] = parent;
                errLinkThird[1] = child;
                flag++;
                if(flag==2)break;
                continue;
            }
            if (nodeArray[child].parent != -1) {
                errLinkSec[0] = nodeArray[child].parent;
                errLinkSec[1] = child;
                errLink[0] = parent;
                errLink[1] = child;
                flag++;
                if(flag==2)break;
                continue;
            }

            nodeArray[child].srcParent = nodeArray[parent].srcParent;
            nodeArray[child].parent = parent;

        }

        for (int i = 0; i < 2; i++) {
            System.out.println(errLink[i] + " " + errLinkSec[i] + " " + errLinkThird[i]);
        }

        if (errLink[1] != 0 && errLink[1] == errLinkSec[1]) {
            if (find(errLink[0]) != find(errLink[1])) {
                errLink = errLinkSec;
            }

        }

        else {
            errLink = errLinkThird;
        }

    }

    private int find(int dst){
        int src = dst;
        while(nodeArray[src].srcParent!=src){
            src = nodeArray[src].srcParent;
        }
        return src;
    }
    private class treeNode{
        treeNode(int i){
            srcParent = i;
        }
        int srcParent;
        int parent = -1;
    }
}



