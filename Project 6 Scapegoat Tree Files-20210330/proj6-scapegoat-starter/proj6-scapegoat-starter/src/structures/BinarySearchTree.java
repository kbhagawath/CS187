package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) {
    
    BSTNode<T> curr = this.root;

    if (t == null){
      throw new NullPointerException();
    }
    else{
      while (curr != null){
        if (curr.getData().compareTo(t) == 0){
          return true;
        }
        else if (curr.getData().compareTo(t) > 0 ){
          curr = curr.getLeft();
        }
        else if ( curr.getData().compareTo(t) < 0){
          curr = curr.getRight();
        }
      }
    }
     return false;
  }

  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  protected BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {

    BSTNode<T> curr = root;

    if ( t == null ){
      throw new NullPointerException();
    }
    else {
      while (curr != null){
        if (curr.getData().compareTo(t) == 0){
          return t;
        }
        else if (curr.getData().compareTo(t) > 0 ){
          curr = curr.getLeft();
        }
        else if ( curr.getData().compareTo(t) < 0){
          curr = curr.getRight();
        }
      }
    }
    return null;
  }


  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
    
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {

    if (root == null){
      return null;
    }
    else {
      return getMinHelper(root);
    }
  }

  private T getMinHelper(BSTNode<T> curr){
    if(curr.getLeft() == null){
      return curr.getData();
    }
    return getMinHelper(curr.getLeft());
  }


  @Override
  public T getMaximum() {

    if (root == null){
      return null;
    }
    else{
      return getMaxHelper(root);
    }
  }

  private T getMaxHelper(BSTNode<T> curr){
    if(curr.getRight() == null){
      return curr.getData();
    }
    return getMaxHelper(curr.getRight());
  }
  

  @Override
  public int height() {
    return heightHelper(root);
  }

  private int heightHelper(BSTNode<T> N1){
    if (N1 == null){
      return -1;
    }
    else {
      return 1+Math.max(heightHelper(N1.getLeft()), heightHelper(N1.getRight()));
    }
  }

  @Override
  public Iterator<T> preorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }

  private void preorderTraverse(Queue<T> queue, BSTNode<T> node){
    if (node != null) {
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue, node.getRight());
    }
  }

  @Override
  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }

  protected void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  @Override
  public Iterator<T> postorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }

  @Override
  public boolean equals(BSTInterface<T> other) {
    if (other == null){
      throw new NullPointerException();
    }
    else if (this.size() == other.size()){
      return equalsHelper(this.root, other.getRoot());
    }
    else {
      return false;
    }
  }
  

  private boolean equalsHelper(BSTNode<T> N1, BSTNode<T> N2){
    if (N1 == N2){
      return true;
    }
    else if (N1 == null || N2 == null){
      return false;
    }
    else {
      boolean equalsLeftHelper = equalsHelper(N1.getLeft(), N2.getLeft());
      boolean equalsRightHelper = equalsHelper(N1.getRight(), N2.getRight());
      return(equalsLeftHelper && equalsRightHelper && N1.getData().equals(N2.getData()));
    }
  }


  @Override
  public boolean sameValues(BSTInterface<T> other) {
    if (other == null){
      throw new NullPointerException();
    }
    Iterator<T> iter1 = this.inorderIterator();
    Iterator<T> iter2 = other.inorderIterator();
   
    while (iter1.hasNext() && iter2.hasNext()){
      if (!(iter1.next().equals(iter2.next()))){
        return false;
      }
    }

    if (!iter1.hasNext() && !iter2.hasNext()){
      return true;
    }
    else{
      return false;
    }
  }

  @Override
  public boolean isBalanced() {
    if (root == null){
      return true;
    }
    else {
      return Math.pow(2, height()) <= size() && size() < Math.pow(2, height() + 1);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void balance() {
    if(!this.isBalanced()){

      Iterator<T> iter = inorderIterator();
      T[] arr = (T[]) new Comparable[size()];

      int i = 0;
      while(iter.hasNext()){
        arr[i] = iter.next();
        i++;
      }
    
      this.root = this.balanceHelper(arr, 0, arr.length-1);
    }
  }

  protected BSTNode<T> balanceHelper(T[] values, int low, int high){
		if (low > high) {
			return null;
		}
		int	middle = (low + high)/2;
		BSTNode<T> N1	= new BSTNode<T>(values[middle], this.balanceHelper(values, low, middle - 1), this.balanceHelper(values, middle + 1, high));	
		return N1;
	}

  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(toDotFormat(tree.getRoot()));
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
