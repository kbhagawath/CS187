package structures;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javax.lang.model.element.Element;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound = 0;


  @Override
  public void add(T t) {
    if (t == null) {
			throw new NullPointerException();
		}

    upperBound++; 

    BSTNode<T> N1 = new BSTNode<T>(t, null, null);
		
    root = super.addToSubtree(root, N1);

		if( this.height() > Math.log(upperBound)/Math.log(3.0/2.0) ) {
      
		  while( (double)subtreeSize(N1) / (double)subtreeSize(N1.getParent()) <= 2.0/3.0) {
			  N1 = N1.getParent();
			}
			N1 = N1.getParent();
			this.balanceSubtree(N1);
		}
	}

	private void balanceSubtree(BSTNode<T> N1) {
		Queue<T> queue = new LinkedList<T>();
		inorderTraverse(queue, N1);
		T[] arr = (T[]) new Comparable[size()];
		queue.toArray(arr);
		BSTNode<T> curr = balanceHelper(arr, 0, arr.length - 1);
		N1.setData(curr.getData());
		N1.setLeft(curr.getLeft());
		N1.setRight(curr.getRight());
	} 

  @Override
  public boolean remove(T element) {

    if (element == null) {
      throw new NullPointerException();
    }
    boolean result = contains(element);
    if (result) {
      root = removeFromSubtree(root, element);
    }
    if (upperBound > 2*size()){
      balance();
      upperBound = size();
    }
    return result;
  }

  
  public static void main(String[] args) {
    BSTInterface<String> tree = new ScapegoatTree<String>();
    /*
    You can test your Scapegoat tree here. */
    for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }

  }
}
