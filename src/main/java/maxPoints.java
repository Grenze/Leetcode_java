import java.util.HashMap;

class maxPoints {
    public int maxPoints(Point[] points) {
        int pointsNum = points.length;
        if(pointsNum<=2) return pointsNum;
        int max = 2;
        HashMap<String, Integer> abCount = new HashMap<String, Integer>(pointsNum*pointsNum);
        HashMap<String, Boolean> curloppCount = new HashMap<String, Boolean>(pointsNum);

        String abStr;
        int startPointRepeat;
        for(int i=0;i<pointsNum-max;i++){
            startPointRepeat = 0;
            for(int k=i+1;k<pointsNum;k++){
                if(same(points[k], points[i])){
                    startPointRepeat++;
                }
            }
            max = max<1 + startPointRepeat?1 + startPointRepeat:max;
            for(int j=i+1;j<pointsNum;j++){
                if(same(points[j], points[i])) continue;
                abStr = abCalc(points[i], points[j]);
                //System.out.println(j+"loop: "+abStr+" repeat: "+startPointRepeat);
                if(abCount.get(abStr)!=null&&curloppCount.get(abStr)==null) continue;
                if(abCount.get(abStr)==null){
                    max = max<2 + startPointRepeat?2 + startPointRepeat:max;
                    abCount.put(abStr, 2 + startPointRepeat);
                    curloppCount.put(abStr, true);
                }
                else{
                    max = max<abCount.get(abStr)+1?abCount.get(abStr)+1:max;
                    abCount.put(abStr, abCount.get(abStr)+1);
                }
            }
            curloppCount.clear();
        }
        return max;
    }

    //same point
    private boolean same(Point A, Point B){
        if(A.x == B.x && A.y == B.y){
            return true;
        }
        return false;
    }

    //y=ax+b
    private String abCalc(Point A, Point B){
        if(A.x==B.x) return A.x+"|INF";
        if(A.y==B.y) return "INF|"+A.y;
        int Ra = B.y-A.y;
        int Rb = A.y*B.x-A.x*B.y;
        if(B.x-A.x<0){
            Ra = -Ra;
            Rb = -Rb;
        }
        if(Rb==0) {
            int gcd = Gcd(Ra, B.x-A.x);
            return Ra/gcd+"|0";
        }
        int gcd = Gcd(Ra, Rb);
        if(gcd!=1){
            Ra = Ra/gcd;
            Rb = Rb/gcd;
        }
        return Ra+"|"+Rb;
    }

    int Gcd(int x, int y){
        int lx = x, ly = y;
        if(x<y){
            lx = y;
            ly = x;
        }
        int t = ly;
        while(lx%ly!=0){
            t = lx%ly;
            lx = ly;
            ly = t;
        }
        return t<0?-t:t;
    }

    private class Point{
        int x;
        int y;
        Point(){x=0;y=0;}
        Point(int a, int b){x=a;y=b;}
    }
}

