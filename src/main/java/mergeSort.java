class mergeSort{
    public int[] sort(int[] array, int low, int high){
        int mid = (low+high)/2;
        if(low<high){
            sort(array, low, mid);
            sort(array, mid+1, high);
            merge(array, low, mid, high);
        }
        return array;
    }

    private void merge(int[] array, int low, int mid, int high){
        int[] temp = new int[high-low+1];
        int i = low;
        int j = mid +1;
        int k = 0;
        while(i<=mid || j<=high){
            if(i==mid+1) {temp[k++] = array[j++];continue;}
            if(j==high+1) {temp[k++] = array[i++];continue;}
            temp[k++] = (array[i]<array[j])?array[i++]:array[j++];
        }

        for(int x=0;x<high-low+1;x++){
            array[low+x] = temp[x];
        }
    }
}