import java.util.ArrayList;

class treeCenter{


    int[] ans;
    treeCenter(int n, int[][] edges){
        treeNode[] NodeList = new treeNode[n];
        Boolean[] removedList = new Boolean[n];
        int removeCount = 0;
        for(int i=0;i<n;i++){
            NodeList[i] = new treeNode();
            removedList[i] = true;
        }
        int size = edges.length;
        int pre, nex;
        for(int i=0;i<size;i++){
            pre = edges[i][0];
            nex = edges[i][1];
            NodeList[pre].degree++;
            NodeList[nex].degree++;
            NodeList[pre].linkNodeList.add(nex);
            NodeList[nex].linkNodeList.add(pre);
        }

        ArrayList<Integer> tempList = new ArrayList<Integer>();
        ArrayList<Integer> tempListSec = new ArrayList<Integer>();

        for(int i=0;i<n;i++){
            if(NodeList[i].degree==1){
                tempList.add(i);
            }
        }

        while (n-removeCount!=2&&n-removeCount!=1){
            for(int i=0;i<tempList.size();i++){
                removedList[tempList.get(i)] = false;
                removeCount++;
                int upper = NodeList[tempList.get(i)].linkNodeList.get(0);
                NodeList[upper].linkNodeList.remove(NodeList[upper].linkNodeList.indexOf(tempList.get(i)));
                NodeList[upper].degree--;
                if(NodeList[upper].degree==1){
                    tempListSec.add(upper);
                }
            }
            tempList = (ArrayList<Integer>)tempListSec.clone();
            tempListSec.clear();

        }
        ans = new int[n-removeCount];
        int order=0;
        for(int i=0;i<removedList.length;i++){
            if(removedList[i]){
                ans[order++] = i;
            }
        }




    }



    private class treeNode{
        public ArrayList<Integer> linkNodeList = new ArrayList<Integer>();
        public int degree;

    }
}
