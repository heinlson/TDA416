import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SLCWithGet<E extends Comparable<? super E>> extends LinkedCollection<E> implements CollectionWithGet<E>{




    @Override
    public boolean add( E element ) {
        if(element == null){
            throw new NullPointerException();
        } else if(head == null){
            head = new Entry(element, null);
            return true;
        } else {
            head = add(element, head);
            return true;
        }
    }

    private Entry add(E element, Entry entry ){
        if (entry == null){
            return new Entry(element, null);
        } else if (element.compareTo(entry.element)<=0) {
            return new Entry(element, entry);
        } else {
            entry.next = add(element, entry.next);
            return entry;
        }
    }


    @Override
    public E get(E element) {
        if(element == null){
            return null;
        } else if(head == null){
            return null;
        } else {
            return get(element,head);
        }
    }

    private E get(E element, Entry entry){
        if(entry == null){
            return null;
        } else if(element.compareTo(entry.element) == 0){
            return entry.element;
        } else {
            return get(element, entry.next);
        }
    }
}
