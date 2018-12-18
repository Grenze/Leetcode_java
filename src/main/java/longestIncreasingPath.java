public class longestIncreasingPath {
    class Solution2 {
        private node[] nodeList;
        int removeCur = 0;
        int addCur = 0;
        private int[] zeroDegree;
        private int[][] move;
        public int longestIncreasingPath(int[][] matrix) {
            if(matrix==null||matrix.length==0||matrix[0].length==0)return 0;
            move = new int[4][2];
            move[0][0] = -1;move[0][1] = 0;
            move[1][0] = 1;move[1][1] = 0;
            move[2][0] = 0;move[2][1] = -1;
            move[3][0] = 0;move[3][1] = 1;
            int xLen = matrix[0].length;
            int yLen = matrix.length;
            int eleNum = xLen*yLen;
            nodeList = new node[eleNum];
            zeroDegree = new int[eleNum];
            //every current anchor has four position, anchor = posX + posY * xLen;
            for(int j=0;j<yLen;j++){
                for(int i=0;i<xLen;i++){
                    int anchor = i + j * xLen;
                    if(nodeList[anchor]==null) nodeList[anchor] = new node();
                    nodeList[anchor].linkSelf(i,j,matrix,xLen,yLen);
                }
            }
            for(int i=0;i<eleNum;i++){
                if(nodeList[i].inDegree==0){
                    zeroDegree[addCur] = i;
                    addCur++;
                }
            }
            int step = 0;
            int level = addCur;
            while(removeCur<eleNum){
                nodeList[zeroDegree[removeCur]].removeSelf();
                removeCur++;
                if(level==removeCur){
                    step++;
                    level = addCur;
                }
            }

            return step;
        }

        class node{
            int inDegree;
            int[] outLink;
            int rec;
            private void linkSelf(int selfX, int selfY, int[][] matrix, int xLen, int yLen){
                for(int i=0;i<move.length;i++){
                    int moveX = selfX+move[i][0];
                    int moveY = selfY+move[i][1];
                    if(moveX>=0 && moveY>=0 && moveX<xLen && moveY<yLen) {
                        if(matrix[moveY][moveX]<matrix[selfY][selfX]){
                            inDegree++;
                            int neighbor = moveX + moveY * xLen;
                            if(nodeList[neighbor]==null) nodeList[neighbor] = new node();
                            nodeList[neighbor].rec++;
                            nodeList[neighbor].outLink[nodeList[neighbor].rec] = selfX + selfY * xLen;
                        }
                    }
                }

            }
            //when its inDegree==0
            public void removeSelf(){
                if(rec==-1) return;
                for(int i=0;i<=rec;i++){
                    int neighbor = outLink[i];
                    nodeList[neighbor].inDegree--;
                    if(nodeList[neighbor].inDegree==0){
                        zeroDegree[addCur] = neighbor;
                        addCur++;
                    }
                }
            }
            node(){
                inDegree = 0;
                outLink = new int[move.length];
                rec = -1;
            }
        }
    }


    class Solution {
        public int longestIncreasingPath(int[][] matrix) {
            if(matrix==null||matrix.length==0||matrix[0].length==0)return 0;
            int xLen = matrix[0].length;
            int yLen = matrix.length;
            int[][] deltaMatrix = new int[yLen][xLen];
            int posX, posY;
            int[][][] message = new int[xLen*yLen][4][2];
            //every current anchor has four position, anchor = posX + posY * xLen;
            //store the old message and updated anchor value
            int maxStep = 1;

            //init deltaMatrix
            for(posX = 0; posX < xLen; posX++) {
                for (posY = 0; posY < yLen; posY++) {
                    deltaMatrix[posY][posX] = 1;
                }
            }
            //init the message by compare current pos to its neighbors
            for(posX = 0; posX < xLen; posX++) {
                for (posY = 0; posY < yLen; posY++) {
                    int originValue = matrix[posY][posX];
                    int curValue = deltaMatrix[posY][posX];
                    int max = curValue;
                    if (posX - 1 >= 0 && matrix[posY][posX - 1] > originValue) {
                        message[posX + posY * xLen][0][0] = deltaMatrix[posY][posX-1];
                        int update = message[posX + posY * xLen][0][0]+curValue;
                        message[posX + posY * xLen][0][1] = update;
                        max = update>max?update:max;
                    }
                    if (posX + 1 < xLen && matrix[posY][posX + 1] > originValue) {
                        message[posX + posY * xLen][1][0] = deltaMatrix[posY][posX+1];
                        int update = message[posX + posY * xLen][1][0]+curValue;
                        message[posX + posY * xLen][1][1] = update;
                        max = update>max?update:max;
                    }
                    if (posY - 1 >= 0 && matrix[posY - 1][posX] > originValue) {
                        message[posX + posY * xLen][2][0] = deltaMatrix[posY-1][posX];
                        int update = message[posX + posY * xLen][2][0]+curValue;
                        message[posX + posY * xLen][2][1] = update;
                        max = update>max?update:max;
                    }
                    if (posY + 1 < yLen && matrix[posY + 1][posX] > originValue) {
                        message[posX + posY * xLen][3][0] = deltaMatrix[posY+1][posX];
                        int update = message[posX + posY * xLen][3][0]+curValue;
                        message[posX + posY * xLen][3][1] = update;
                        max = update>max?update:max;
                    }
                    if(max>curValue){
                        deltaMatrix[posY][posX] = max;
                        maxStep = max>maxStep?max:maxStep;
                    }
                }
            }

            int round = 0;
            int update;
            //update the deltaMatrix by message until no new update
            boolean flag = true;
            while(flag){
                round++;
                flag = false;
                for(posX=0;posX<xLen;posX++) {
                    for (posY = 0; posY < yLen; posY++) {
                        int curValue = deltaMatrix[posY][posX];
                        if(curValue<=round) continue;
                        int max = curValue;
                        for(int i=0;i<4;i++){
                            if(message[posX + posY * xLen][i][0]<round)continue;
                            switch (i){
                                case 0:
                                    if(deltaMatrix[posY][posX-1] == message[posX + posY * xLen][i][0])continue;
                                    update = message[posX + posY * xLen][i][1]+deltaMatrix[posY][posX-1]-message[posX + posY * xLen][i][0];
                                    message[posX + posY * xLen][i][1] = update;
                                    max = update>max?update:max;
                                    message[posX + posY * xLen][i][0] = deltaMatrix[posY][posX-1];
                                    break;
                                case 1:
                                    if(deltaMatrix[posY][posX+1] == message[posX + posY * xLen][i][0])continue;
                                    update = message[posX + posY * xLen][i][1]+deltaMatrix[posY][posX+1]-message[posX + posY * xLen][i][0];
                                    message[posX + posY * xLen][i][1] = update;
                                    max = update>max?update:max;
                                    message[posX + posY * xLen][i][0] = deltaMatrix[posY][posX+1];
                                    break;
                                case 2:
                                    if(deltaMatrix[posY-1][posX] == message[posX + posY * xLen][i][0])continue;
                                    update = message[posX + posY * xLen][i][1]+deltaMatrix[posY-1][posX]-message[posX + posY * xLen][i][0];
                                    message[posX + posY * xLen][i][1] = update;
                                    max = update>max?update:max;
                                    message[posX + posY * xLen][i][0] = deltaMatrix[posY-1][posX];
                                    break;
                                case 3:
                                    if(deltaMatrix[posY+1][posX] == message[posX + posY * xLen][i][0])continue;
                                    update = message[posX + posY * xLen][i][1]+deltaMatrix[posY+1][posX]-message[posX + posY * xLen][i][0];
                                    message[posX + posY * xLen][i][1] = update;
                                    max = update>max?update:max;
                                    message[posX + posY * xLen][i][0] = deltaMatrix[posY+1][posX];
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(max > curValue){
                            deltaMatrix[posY][posX] = max;
                            maxStep = max>maxStep?max:maxStep;
                            flag = true;
                        }
                    }
                }
            }
            return maxStep;
        }
    }
}
