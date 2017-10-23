package lispkit.secd;

public interface Stack<E> {

    E pop();
    E peek();
    E push(E element);
    boolean empty();

}
