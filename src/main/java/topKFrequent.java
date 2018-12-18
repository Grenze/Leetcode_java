import java.util.ArrayList;
import java.util.List;

//first get every integer's count array,
//then figure out how much count can make the integer rank top K
//time: O(N)
class topKFrequent {
    public List<Integer> topKFrequent(int[] nums, int k) {
        if(nums == null || nums.length == 0) return null;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for(int i : nums){
            if(i < min) min = i;
            if(i > max) max = i;
        }

        int[] count = new int[max-min+1];   // 各个数字出现的频数

        for(int i : nums){
            count[i - min]++;
        }

        int max_count = Integer.MIN_VALUE;

        for(int i : count){
            if(i > max_count)
                max_count = i;
        }

        int[] aid = new int[max_count+1];   // 各个频数所包含的key数

        for(int i : count){
            if(i > 0){
                aid[i]++;
            }
        }

        int min_count = 1;
        int curr = 0;
        for(int i = aid.length-1; i >=1; i--){
            if(aid[i] > 0){
                curr += aid[i];
                if(curr >= k){
                    min_count = i;
                    break;
                }
            }
        }

        List<Integer> ans = new ArrayList<Integer>(k);
        for(int i = 0; i < count.length; i++){
            if(count[i] >= min_count){
                ans.add(i+min);
            }
        }

        return ans;
    }

}