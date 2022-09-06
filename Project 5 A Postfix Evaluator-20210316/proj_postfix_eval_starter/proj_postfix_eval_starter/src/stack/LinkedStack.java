package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure to allow for
 * unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {
  
  private LLNode<T> head;
  private int size;  

  /** {@inheritDoc} */
  @Override
  public T pop() throws StackUnderflowException {
    if (isEmpty()){
      throw new StackUnderflowException();
    }
    T temp = head.getData();
    head = head.getNext();
    size--;
    return temp;
  }

  /** {@inheritDoc} */
  @Override
  public T top() throws StackUnderflowException {
    if (isEmpty()){
      throw new StackUnderflowException();
    }
    return head.getData();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEmpty() {
    return ( head == null );
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return size;
  }

  /** {@inheritDoc} */
  @Override
  public void push(T elem) {
    LLNode<T> newNode = new LLNode<T>(elem);
    newNode.setNext(head);
    head = newNode;
    size++;
  }
  
}
