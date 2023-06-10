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
   * 
   * @param comparator comparator object to define a sorting order for the heap
   *                   elements.
   * @param isMaxHeap  Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
    this.comparator = comparator;
    this.isMaxHeap = isMaxHeap;
    heap = (T[]) new Object[INIT_SIZE];
    numElements = 0;
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time. Note: When enqueue is called, an entry is placed at the
   * next available index in the array and then this method is called on that
   * index.
   *
   * @param index the index to bubble up
   */
  public void bubbleUp(int index) {
    if (index == 0) {
      return;
    }
    int parentIndex = (index - 1) / 2;
    if (compare(heap[index], heap[parentIndex]) <= 0) {
      return;
    }
    swapNodes(index, parentIndex);
    bubbleUp(parentIndex);

  }

  private void swapNodes(int index, int other) {
    T temp = heap[index];
    heap[index] = heap[other];
    heap[other] = temp;
  }

  private int getLeftChildOf(int parentIndex) {
    int leftchild = (2 * parentIndex + 1);
    return leftchild;
  }

  private int getRightChildOf(int parentIndex) {
    int rightchild = (2 * parentIndex + 2);
    return rightchild;
  }

  private int getParentOf(int childIndex) {
    int parent = (childIndex - 1) / 2;
    return parent;
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time. Note: When remove is called, if there are
   * elements remaining in this the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   */
  public void bubbleDown(int index) {
    int childIndex = getLeftChildOf(index);
    T value = heap[index];
    while (childIndex < this.size()) {
      T maxVal = value;
      int maxIndex = -1;
      for (int i = 0; i < 2 && i + childIndex < this.size(); i++) {
        if (compare(heap[i + childIndex], maxVal) > 0) {
          maxVal = heap[i + childIndex];
          maxIndex = i + childIndex;
        }
      }
      if (maxVal == value) {
        return;
      } else {
        swapNodes(index, maxIndex);
        index = maxIndex;
        childIndex = getLeftChildOf(index);
      }
    }
  }

  /**
   * Test for if the queue is empty.
   * 
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
    if (numElements != 0) {
      return isEmpty;
    }
    return !isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * 
   * @return the size
   */
  public int size() {
    int size = numElements;
    return size;
  }

  /**
   * Compare method to implement max/min heap behavior. It changes the value of a
   * variable, compareSign, beased on the state of the boolean variable isMaxHeap.
   * It then calls the compare method from the comparator object and multiplies
   * its output by compareSign.
   * 
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if
   *         {@code element1 == element2}, negative int otherwise (if isMaxHeap),
   *         return negative int if {@code element1 > element2}, 0 if
   *         {@code element1 == element2}, positive int otherwise (if !
   *         isMinHeap).
   */
  public int compare(T element1, T element2) {
    int result = 0;
    int compareSign = -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap
   * without removing the element.
   * 
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
    T data = null;
    if (this.isEmpty()) {
      throw new QueueUnderflowException();
    }
    data = heap[0];

    return data;
  }

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority
   * in the heap.
   * 
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeue() throws QueueUnderflowException {
    T data = null;
    if (this.isEmpty()) {
      throw new QueueUnderflowException();
    }
    data = heap[0];
    if (this.size() == 1) {
      heap[0] = null;
      --numElements;
    } else {
      heap[0] = heap[this.size() - 1];
      heap[this.size()] = null;
      --numElements;
      bubbleDown(0);
    }
    return data;
  }

  /**
   * Enqueue the element.
   * 
   * @param the new element
   */
  public void enqueue(T newElement) {
    if (newElement == null)
      return;
    if (this.isEmpty()) {
      heap[0] = newElement;
      ++numElements;
    } else if (this.size() < heap.length) {
      heap[this.size()] = newElement;
      bubbleUp(this.size());
      ++numElements;
    } else {
      expandCapacity();
      heap[this.size()] = newElement;
      bubbleUp(this.size());
      ++numElements;

    }
  }

  private void expandCapacity() {
    T[] newarray = (T[]) new Object[numElements * 2];
    for (int i = 0; i < heap.length; i++) {
      newarray[i] = heap[i];
    }
    heap = newarray;
  }

}