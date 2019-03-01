
import tester.*;
import java.util.*;

class CompareWeight implements Comparator<Edge> {

  @Override
  public int compare(Edge edge1, Edge edge2) {
    return edge1.weight - edge2.weight;
  }

}

class CompareInt implements Comparator<Integer> {

  @Override
  public int compare(Integer o1, Integer o2) {
    return o1 - o2;
  }
}

/**
 * Heap Sort implementation.
 */
public class HeapSort<T> extends SortingHeapSort<T> {

  // In ArrayUtils
  void swap(List<T> arr, int index1, int index2) {
    arr.set(index2, arr.set(index1, arr.get(index2)));
  }

  @Override
  /*
   * Given a list and a comparator, sort the list using heap sort.
   * 
   * @param list the list to sort
   * 
   * @param comp the comparator
   * 
   * @return the sorted list
   */
  public List<T> heapsort(List<T> list, Comparator<T> comp) {
    List<T> sortedList = new ArrayList<T>();
    this.buildHeap(list, comp);
    for (int i = list.size() - 1; i >= 0; i--) {
      sortedList.add(list.remove(0));
      this.buildHeap(list, comp);
    }
    return sortedList;
  }

  public void buildHeap(List<T> list, Comparator<T> comp) {
    for (int i = (list.size() / 2) - 1; i >= 0; i--) {
      this.heapify(list, i, comp);
    }
  }

  public void heapify(List<T> list, int i, Comparator<T> comp) {
    int largest = i;
    int left = (2 * i) + 1;
    int right = (2 * i) + 2;

    if (left < list.size() && comp.compare(list.get(left), list.get(largest)) < 0) {
      largest = left;
    }
    if (right < list.size() && comp.compare(list.get(right), list.get(largest)) < 0) {
      largest = right;
    }
    if (largest != i) {
      this.swap(list, largest, i);
      this.heapify(list, largest, comp);
    }

  }
}

class ExamplesHeapSort {
  HeapSort<Integer> heapSort = new HeapSort<>();
  Comparator<Integer> comp = new CompareInt();

  List<Integer> list = new ArrayList<>(Arrays.asList(4, 6, 3, 7, 2, 11, 1, 19));
  List<Integer> list2 = new ArrayList<>(Arrays.asList(
      20,19,18,17,16,15,14,13,12,11,10,5,8,4,3,9,2,1,6,7));

  void testHeapSort(Tester t) {
    t.checkExpect(this.heapSort.heapsort(list, comp),
        new ArrayList<>(Arrays.asList(1,2,3,4,6,7,11,19)));
    //t.checkExpect(this.heapSort.heapsort(list2, comp), 
    //  new ArrayList<>(Arrays.asList(
    //    1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)));
    this.heapSort.buildHeap(list, comp);
    t.checkExpect(this.list, null);
    t.checkExpect(this.heapSort.heapsort(list, comp), null);
  }


}
