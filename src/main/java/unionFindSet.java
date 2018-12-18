
class unionFindSet{
    int[] uFS;
    int[] errLink;
    unionFindSet(int[][] edges){
        errLink = new int[2];
        int vertexNum = edges.length+1;
        uFS = new int[vertexNum];
        for(int i=1;i<vertexNum;i++){
            uFS[i] = i;
        }
        int begin;
        int end;
        int srcBegin;
        int srcEnd;
        for(int i=0;i<vertexNum-1;i++){
            begin = edges[i][0];
            end = edges[i][1];
            srcBegin = find(begin);
            srcEnd = find(end);
            if(srcBegin==srcEnd){
                errLink[0]=begin;
                errLink[1]=end;
            }
            uFS[srcEnd] = uFS[srcBegin];
        }
    }
    private int find(int dst){
        int src = dst;
        while (uFS[src]!=src){
            src = uFS[src];
        }
        return src;
    }
}

