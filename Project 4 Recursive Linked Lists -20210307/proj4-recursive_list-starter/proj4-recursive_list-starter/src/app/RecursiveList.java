package app;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {

  private int size;
  private Node<T> head = null;
  
  public RecursiveList() {
    this.head = null;
    this.size = 0;
  }

  public RecursiveList(Node<T> first) {
    this.head = first;
    this.size = 1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void insertFirst(T elem) {
      //TODO: Implement this method.
      
      if (elem == null){
        throw new NullPointerException();
      }
      this.insertHelper(elem, 0, this.head);     
  }

  @Override
  public void insertLast(T elem) {
      //TODO: Implement this method.
      if (elem == null){
        throw new NullPointerException();
      }
      this.insertHelper(elem, this.size, this.head);

  }

  @Override
  public void insertAt(int index, T elem) {
      //TODO: Implement this method.
      
      if (elem == null){
        throw new NullPointerException();
      }
      else if ( index < 0 || index > this.size){
        throw new IndexOutOfBoundsException();
      }
      else{
        this.insertHelper(elem, index, this.head);
     }

  }

  private void insertHelper( T elem, int index, Node<T> curr){
    if ( index == 0 ){
      Node<T> newNode = new Node<T>(elem, this.head);
      this.size += 1;
      this.head = newNode;
    }
    else if ( index == 1){
      Node<T> newNode = new Node<T>(elem, curr.getNext());
      curr.setNext(newNode);
      this.size += 1;
    }
    else {
      this.insertHelper(elem, index-1, curr.getNext());
    }
  }

  @Override
  public T removeFirst() {
    T removedItem = null;
      //TODO: Implement this method.
      if ( isEmpty() ){
        throw new IllegalStateException();
      }
    return this.removeHelper(0, this.head);
  }

  @Override
  public T removeLast() {
    T removedItem = null;
      //TODO: Implement this method.
      if ( isEmpty() == true){
        throw new IllegalStateException();
      }
    return this.removeHelper(this.size-1, this.head);
  }

  @Override
  public T removeAt(int i) {
    T removedItem = null;
      //TODO: Implement this method.
      if ( isEmpty() ){
        throw new IllegalStateException();
      }
      else if ( i < 0 || i >= this.size ){
        throw new IndexOutOfBoundsException();
      }
    return this.removeHelper(i, this.head);
  }

  private T removeHelper( int index, Node<T> curr){
    T item;
    if ( index == 0 ){
      item = this.head.getData();
      this.head = this.head.getNext();  
      this.size -= 1;
    }
    else if ( index == 1){
      item = curr.getNext().getData();
      curr.setNext(curr.getNext().getNext());
      this.size -= 1;
    }
    else {
      return this.removeHelper(index-1, curr.getNext());
    }
    return item;
  }

  @Override
  public T getFirst() {
    T item = null;
      //TODO: Implement this method.
      if ( isEmpty() == true){
        throw new IllegalStateException();
      }
    return this.getHelper(0, this.head);
  }

  @Override
  public T getLast() {
    T item = null;
      //TODO: Implement this method.
      if ( isEmpty() ){
        throw new IllegalStateException();
      }
    return this.getHelper(this.size-1, this.head);
  }

  @Override
  public T get(int i) {
    T item = null;
      //TODO: Implement this method.
      if ( i < 0 || i >= this.size ){
        throw new IndexOutOfBoundsException();
      }
    return this.getHelper(i, this.head);
  }

  private T getHelper( int index, Node<T> curr){
    if ( index == 0 ){
      return curr.getData();
    }
    else {
      return this.getHelper(index-1, curr.getNext());
    }
  }
  @Override
  public void remove(T elem) {
      //TODO: Implement this method.
      if ( elem == null ){
        throw new NullPointerException();
      }
      this.removeElementHelper(elem, this.head);
  }

  private void removeElementHelper(T elem, Node<T> curr){
    if ( curr == null ){
      throw new ItemNotFoundException();
    }
    else if( curr.getNext() != null && curr.getNext().getData().equals(elem)){
      curr.setNext(curr.getNext().getNext());
      this.size -= 1;
    }
    else {
      this.removeElementHelper(elem, curr.getNext());
    }

  }

  @Override
  public int indexOf(T elem) {
    int index = -1;
      //TODO: Implement this method.
      Node<T> newNode;
      if ( elem == null ){
        throw new NullPointerException();
      }
      index = this.indexHelper(elem, 0, this.head);
   return index;
  }

  private int indexHelper( T elem, int index, Node<T> curr){
    if( curr == null ){
      return -1;
    }
    else if ( curr.getData().equals(elem)){
      return index;
    }
    else{
      return this.indexHelper(elem, index+1, curr.getNext());
    }
  }


  @Override
  public boolean isEmpty() {
    boolean empty = false;
      //TODO: Implement this method.
      if ( this.size == 0 ){
        empty = true;
      }

    return empty;
  }


  public Iterator<T> iterator() {
    Iterator<T> iter = null;
      //TODO: Implement this method.
      iter = new LinkedNodeIterator<>(head);
   return iter;
  }
}
