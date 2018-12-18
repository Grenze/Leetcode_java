import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

class mergeKListsWithHeap{

    ListNode ans;
    mergeKListsWithHeap(ListNode[] lists){
        int listNum = lists.length;
        if(listNum==0)return;
        Comparator<ListNode> cmp;
        cmp = new Comparator<ListNode>() {
            public int compare(ListNode e1, ListNode e2) {
                return e1.val - e2.val;
            }
        };
        Queue<ListNode> heap = new PriorityQueue<ListNode>(listNum,cmp);
        for(int i=0;i<listNum;i++){
            if(lists[i]!=null) heap.add(lists[i]);
        }
        ListNode temp = heap.poll();//first
        if(temp==null) return;
        if(temp.next!=null) heap.add(temp.next);//this List's next
        ans = temp;
        ListNode pre;
        pre = ans;
        while(!heap.isEmpty()){
            temp = heap.poll();
            pre.next = temp;
            pre =temp;
            if(temp.next!=null) heap.add(temp.next);
        }
    }


}

class ListNode{
    int val;
    ListNode next;
    ListNode(int x){ val = x;}
}


