package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      heap = (T[]) new Object[INIT_SIZE + 1];
      this.isMaxHeap = isMaxHeap;
      numElements = 0;
      this.comparator = comparator;
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   */
  public void bubbleUp(int index) {
    if (index < 0 || index > numElements){
      return;
    }
    if ( index > 0 ){
      while(index > 1 && (compare(heap[index], heap[index/2]) > 0)){
          Swap(index, index/2);
          index = index/2;
        } 
      }
    }


  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   */
  public void bubbleDown(int index) {
    if (index < 0 || index > numElements){
      return;
    }
    if ( index > 0 ){
      while( numElements >= 2*index){
        int i = 2*index;
        if ( i < numElements && compare(heap[i], heap[i+1]) < 0){
            i++;
          }
        if (compare(heap[index], heap[i]) > 0){
          break;
        }
        Swap(i, index);
        index = i;
      }
    }
  }

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
      if (numElements == 0){
        isEmpty = true;
      }
    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int size(){
    int size = -100;
      size = numElements;
      return size;
  }

  /**
   * Compare method to implement max/min heap behavior.  It calls the comparae method from the 
   * comparator object and multiply its output by 1 and -1 if max and min heap respectively.
   * TODO: implement the heap compare method
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, negative int otherwise
   */
  public int compare(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
     T data = null;
      if (isEmpty()){
        throw new QueueUnderflowException();
      }
      data = heap[1];
    return data;
  }  

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeue() throws QueueUnderflowException{
    T data = null;
      if (isEmpty()){
        throw new QueueUnderflowException();
      }
      Swap(1, numElements);
      data = heap[numElements];
      heap[numElements] = null;
      numElements--;
      if (!isEmpty()){
        bubbleDown(1);
      }
    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueue(T newElement) {
      if (numElements >= (heap.length - 1)){
        expandCapacity(2*heap.length);
      }
      heap[++numElements] = newElement;
      bubbleUp(numElements);
  }

  private void Swap(int i, int j) {
    T swap = heap[i];
    heap[i] = heap[j];
    heap[j] = swap;
  }

  private void expandCapacity(int cap){
    T[] temp = (T[]) new Object[cap];
    for ( int i = 1; i <= numElements; i++){
      temp[i] = heap[i];
    }
    heap = temp;
  }

}