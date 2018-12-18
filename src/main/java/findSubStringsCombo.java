import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class findSubStringsCombo {
    public List<Integer> findSubStringsCombo(String s, String[] words) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if(s.length()==0 || words.length==0) return ans;
        int wordsNum = words.length;
        int wordsLength = words[0].length();
        int slideWindow = wordsNum * wordsLength;

        HashMap<String, Integer> pos = new HashMap<String, Integer>(wordsNum);
        int[] wordCount = new int[wordsNum];
        int[] charCount = new int[256];
        for(int i=0;i<wordsNum;i++){
            if(pos.get(words[i])==null){
                pos.put(words[i], i);
            }
            wordCount[pos.get(words[i])]++;
            if(words[i].length()!=wordsLength) return ans;
            for(int j=0;j<words[i].length();j++){
                charCount[words[i].charAt(j)]++;
            }
        }

        //rough filter
        ArrayList<String> roughResult = new ArrayList<String>();
        ArrayList<Integer> roughPos = new ArrayList<Integer>();

        int[] temp = new int[0];
        int anchor = 0;
        boolean changed = true;
        for(int i=0;i<s.length();){
            if(i==anchor&&changed) {
                temp  = new int[256];
                changed = false;
            }
            if(charCount[s.charAt(i)]>temp[s.charAt(i)]){
                changed = true;
                temp[s.charAt(i)]++;
                i++;
                if(i-anchor==slideWindow){
                    roughResult.add(s.substring(anchor,i));
                    roughPos.add(anchor);
                    temp[s.charAt(anchor)]--;
                    anchor++;
                }
            }
            else{
                anchor++;
                i=anchor;
            }

        }
        //fine filter

        String roughStr;

        Integer roughStrSubPos;

        boolean filtered;

        for(int i=0;i<roughResult.size();i++){
            roughStr = roughResult.get(i);
            filtered = true;
            temp = new int[wordsNum];
            for(int j=0;j<slideWindow;){
                roughStrSubPos = pos.get(roughStr.substring(j,j+wordsLength));
                if(roughStrSubPos!=null&&temp[roughStrSubPos]<wordCount[roughStrSubPos]){
                    temp[pos.get(roughStr.substring(j,j+wordsLength))]++;
                    j+=wordsLength;
                }
                else{
                    filtered = false;
                    break;
                }
            }
            if(filtered){
                ans.add(roughPos.get(i));
            }
        }

        return ans;

    }
}
