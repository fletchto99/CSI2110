import java.util.ArrayList;

/**
 * Implements bubble sort.
 */
public class MergeSort<T  extends Comparable<? super T>> {

    public MergeSort(T[] sequence) {
        this.mergeSort(sequence, 0 , sequence.length);
    }

    private void mergeSort(T[] sequence, int start, int size) {
        if(size < 2) {
            return;
        }
        mergeSort(sequence, start, size/2);
        int second = start + (size/2);
        int secondsize = size - (size/2);
        mergeSort(sequence, second, secondsize);
        merge(sequence, start, size/2, second, secondsize);
    }

    private void merge(T[] arr, int first, int firstsize, int second, int secondsize) {
        ArrayList<T> temp = new ArrayList<>(firstsize+secondsize);
        int first_end = first + firstsize;
        int second_end = second + secondsize;
        int i,j,k;
        for(i=first,j=second,k=0; (i<first_end) && (j<second_end); k++) {
            if (arr[i].compareTo(arr[j]) < 0) {
                temp.add(k,arr[i++]);
            } else {
                temp.add(k, arr[j++]);
            }
        }
        while(i < first_end) {
            temp.add(k++, arr[i++]);
        }
        while(i < first_end) {
            temp.add(k++, arr[j++]);
        }
        for(i = first, j = 0; j < temp.size(); i++, j++) {
            arr[i] = temp.get(j);
        }
    }
}
