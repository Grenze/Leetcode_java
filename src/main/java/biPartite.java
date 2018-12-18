import java.util.Stack;

class biPartite{

    public Boolean bool = true;

    //A->B->A->'''
    biPartite(int[][] edges){
        int nodeNum = edges.length;
        int[] group = new int[nodeNum];
        Stack<Integer> Agroup = new Stack<Integer>();
        Stack<Integer> Bgroup = new Stack<Integer>();

        int count = 0;

        for(int i=0;i<nodeNum;i++){
            if(edges[i].length==0){
                group[i] = 3;
                count++;
            }
        }
        int temp;
        while(count<nodeNum){
            if(Agroup.empty()){
                for(int i=0;i<nodeNum;i++){
                    if(group[i]==0){
                        Agroup.push(i);
                        group[i] = 1;
                        break;
                    }
                }
            }
            while(!Agroup.empty()){
                temp = Agroup.pop();
                count++;
                for(int i=0;i<edges[temp].length;i++){
                    if(group[edges[temp][i]]==1){
                        bool = false;
                        return;
                    }
                    if(group[edges[temp][i]]==0){
                        Bgroup.push(edges[temp][i]);
                        group[edges[temp][i]] = 2;
                    }
                }
            }
            while(!Bgroup.empty()){
                temp = Bgroup.pop();
                count++;
                for(int i=0;i<edges[temp].length;i++){
                    if(group[edges[temp][i]]==2){
                        bool = false;
                        return;
                    }
                    if(group[edges[temp][i]]==0){
                        Agroup.push(edges[temp][i]);
                        group[edges[temp][i]] = 1;
                    }
                }
            }

        }


    }
}