import java.util.HashMap;
import java.util.Map;

class dynamicProgram{

    public int rob(int[] nums) {
        int len = nums.length;
        if(len==0) return 0;
        if(len==1) return nums[0];
        int[] ans = new int[len];
        ans[0] = nums[0];
        ans[1] = nums[1];
        int preMax = nums[0];
        for(int i=2;i<len;i++){
            ans[i] = nums[i] + preMax;
            preMax = preMax<ans[i-1]?ans[i-1]:preMax;
        }
        return preMax>ans[len-1]?preMax:ans[len-1];
    }

    public int coinProgram(){
        int[] a = {1,3,5};//1,3,5 yuan
        int sum = 11;//sum 11 yuan
        int[] dp = new int[12];//1->11 yuan least coin number
        for(int i=0;i<=sum;i++) dp[i] = i;//max

        for(int i=1;i<=sum;i++){
            for(int j=0;j<3;j++){//for every ? yuan, forward 1,3,5 to get the least coin number
                if(i>=a[j] && dp[i-a[j]] + 1 < dp[i]){
                    dp[i] = dp[i-a[j]] + 1;
                }
            }
            //after the inner loop dp[i] is the optimized ans
            for(int k=0;k<=sum;k++){
                System.out.print(dp[k]+" ");
            }
            System.out.println();
        }

        System.out.println(dp[sum]);

        return 0;
    }

    public boolean canPartition(int[] nums)
    {
        if(nums==null || nums.length==0) return true;

        int n=nums.length;
        int sum=0;
        for(int i=0;i<n;i++)
            sum+=nums[i];

        if(sum%2!=0) return false;
        int target=sum/2;


        boolean[] dp=new boolean[target+1];
        dp[0]=true;

        for(int i=0;i<n;i++)
        {
            for(int j=target;j>=nums[i];j--)
                dp[j]=dp[j] || dp[j-nums[i]];
        }
        return dp[target];

    }

    public int maximalSquare(char[][] matrix) {
        if(matrix.length==0 || matrix[0].length==0) return 0;
        char res = '0';
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j] == '1'){
                    if(i*j!=0) {
                        matrix[i][j] = matrix[i-1][j];
                        if(matrix[i-1][j-1]<matrix[i][j]){
                            matrix[i][j] = matrix[i-1][j-1];
                        }
                        if(matrix[i][j-1]<matrix[i][j]){
                            matrix[i][j] = matrix[i][j-1];
                        }
                        matrix[i][j] += 1;
                    }
                    res = matrix[i][j]>res?matrix[i][j]:res;
                }
            }
        }
        return (res-'0')*(res-'0');
    }

    public int minDistance(String word1, String word2) {
        int w1 = word1.length();
        int w2 = word2.length();
        if(w1==0||w2==0) return (word1+word2).length();

        int[][] matrix = new int[w1+1][w2+1];

        for(int i=0;i<=w1;i++){
            matrix[i][0] = i;
        }

        for(int j=0;j<=w2;j++){
            matrix[0][j] = j;
        }

        //1<=i<=w1, 1<=j<=w2
        int temp;

        for(int i=1;i<=w1;i++) {
            for (int j = 1; j <= w2; j++) {
                //delete insert change are symmetrical
                //[i-1][j] + 1, [i][j-1] + 1, [i-1][j-1] + 0(word1[i-1]==word[j-1]) or 1
                //current char to compare is word1[i-1] and word2[j-1]
                matrix[i][j] = matrix[i - 1][j - 1] + (word1.charAt(i-1) == word2.charAt(j-1) ? 0 : 1);
                temp = Math.min(matrix[i - 1][j], matrix[i][j - 1]) + 1;
                matrix[i][j] = temp < matrix[i][j] ? temp : matrix[i][j];
            }
        }
        /*for(int i=0;i<=w1;i++) {
            for (int j = 0; j <=w2; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }*/

        return matrix[w1][w2];

    }

    //sum%k repeat and current - pre > 2
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(){{put(0,-1);}};
        int runningSum = 0;
        for (int i=0;i<nums.length;i++) {
            runningSum += nums[i];
            if (k != 0) runningSum %= k;
            Integer prev = map.get(runningSum);
            if (prev != null) {
                if (i - prev > 1) return true;
            }
            else map.put(runningSum, i);
        }
        return false;
    }



    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2)
            return nums.length;
        int[] up = new int[nums.length];
        int[] down = new int[nums.length];
        up[0] = down[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = down[i - 1] + 1;
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                down[i] = up[i - 1] + 1;
                up[i] = up[i - 1];
            } else {
                down[i] = down[i - 1];
                up[i] = up[i - 1];
            }
        }
        return Math.max(down[nums.length - 1], up[nums.length - 1]);
    }

    public int bagProgram(){
        int[] weight = {0,2,2,6,5,4};
        int[] value = {0,6,3,5,4,6};
        int[][] table = new int[6][11];//bag capability = 10, table of total value

        for(int i=1;i<=5;i++) {
            for (int j = 1; j <= 10; j++) {
                //choose i or not
                if (weight[i] > j) {
                    table[i][j] = table[i - 1][j];
                } else {
                    table[i][j] = Math.max(table[i - 1][j - weight[i]] + value[i], table[i - 1][j]);
                }
            }
        }

        for(int i=1;i<=5;i++) {
            for (int j = 1; j <= 10; j++) {
                System.out.print(table[i][j]+" \t");
            }
            System.out.println();
        }
        return 0;
    }

    /*
    * dp[x][g][p] means in the first x crimes, with g people rest, still need to create p profits, how many possibilities
      border: dp[-1][G][P]=1

    dp[x-1][g][p] contributes to
        1.dp[x][g][p], this means this job is not done
        2.dp[x][g-group[x]][max(0,p-profit[x])] (when possible g), this means the x-th job is done.
    answer is to sum dp[n][0-G][0] up.*/
    private int modulo = 1000000007;
    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        int[][][] dp = new int[101][101][101];
        //dp[x][g][p] means in the first x crimes, with g people rest, still need to create p profits
        //dp[-1][G][P]=1;
        int n = group.length;
        dp[0][G][P] = 1;
        for(int i=0;i<n;i++){
            for(int g=G;g>=0;g--){
                for(int p=P;p>=0;p--){
                    if(dp[i][g][p]>0){
                        dp[i+1][g][p]+=dp[i][g][p];
                        dp[i+1][g][p]%=modulo;
                        if(g<group[i]) continue;
                        dp[i+1][g-group[i]][Math.max(0,p-profit[i])]+=dp[i][g][p];
                        dp[i+1][g-group[i]][Math.max(0,p-profit[i])]%=modulo;
                    }
                }
            }
        }
        int ans = 0;
        for(int g=G;g>=0;g--) ans = (ans+dp[n][g][0])%modulo;
        return ans;
        }


}