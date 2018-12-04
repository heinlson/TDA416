public class SplayWithGet<E extends Comparable<? super E>>
							extends BinarySearchTree<E> implements CollectionWithGet<E> {
    @Override
    public Entry find(E elem, Entry t){
        if ( t == null || elem == null)
            return null;
        else {
            int cmp = elem.compareTo( t.element );
            if ( cmp  < 0 ) {
                return find(elem, t.left);
            } else if ( cmp > 0 ) {
                return find(elem, t.right);
            } else{
                splay(t);
                return root;
            }
        }
    }

    private void splay (Entry t){
        if(t.parent == null){//Is t root?
            root = t;
            return;
        } else if (t.parent.parent == null){ //Is t child of root?
            if (isLeftChild(t)){
                rotateRight(t.parent);
            }else {
                rotateLeft(t.parent);
            }
            root = t.parent;
            return;
        } else { //What relation does t have to its grandparent?
            Entry grandparent = t.parent.parent;
            if (isLeftChild(t.parent)){
                if(isLeftChild(t)){
                    rotateRightRight(grandparent);
                }else{
                    rotateLeftRight(grandparent);
                }
            } else {
                if (isLeftChild(t)) {
                    rotateRightLeft(grandparent);
                } else {
                    rotateLeftLeft(grandparent);
                }
            }
            splay(grandparent); //Recursive call to splay with the original grandparent as next t
            return;
        }
    }

    private boolean isLeftChild(Entry t) {
        return (t.parent.left == t);
    }


    private void rotateRight( Entry x ) {
        Entry   y = x.left;
        E    temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.left    = y.left;
        if ( x.left != null )
            x.left.parent   = x;
        y.left    = y.right;
        y.right   = x.right;
        if ( y.right != null )
            y.right.parent  = y;
        x.right   = y;
    }

    private void rotateLeft( Entry x ) {
        Entry  y  = x.right;
        E temp    = x.element;
        x.element = y.element;
        y.element = temp;
        x.right   = y.right;
        if ( x.right != null )
            x.right.parent  = x;
        y.right   = y.left;
        y.left    = x.left;
        if ( y.left != null )
            y.left.parent   = y;
        x.left    = y;
    }

    private void rotateLeftRight( Entry x ) {
        Entry   y = x.left,
                z = x.left.right;
        E       e = x.element;
        x.element = z.element;
        z.element = e;
        y.right   = z.left;
        if ( y.right != null )
            y.right.parent = y;
        z.left    = z.right;
        z.right   = x.right;
        if ( z.right != null )
            z.right.parent = z;
        x.right   = z;
        z.parent  = x;
    }

    private void rotateRightLeft( Entry x ) {
        Entry  y  = x.right,
                z  = x.right.left;
        E      e  = x.element;
        x.element = z.element;
        z.element = e;
        y.left    = z.right;
        if ( y.left != null )
            y.left.parent = y;
        z.right   = z.left;
        z.left    = x.left;
        if ( z.left != null )
            z.left.parent = z;
        x.left    = z;
        z.parent  = x;
    }

    private void rotateRightRight( Entry x ) {
        //swap data
        Entry y = x.left;
        Entry z = x.left.left;
        E e = x.element;
        x.element = z.element;
        z.element = e;

        //change relations for "A,B,C,D"
        x.left = z.left;
        if(x.left != null){
            x.left.parent = x;
        }
        y.left = z.right;
        if(y.left != null){
            y.left.parent = y;
        }
        z.left = y.right;
        if(z.left != null){
            z.left.parent = z;
        }
        z.right = x.right;
        if(z.right != null){
            z.right.parent = z;
        }
        //change relations for x,y,z
        x.right = y;
        //
        y.right = z;

    }

    private  void rotateLeftLeft(Entry x){
        //swap data
        Entry y = x.right;
        Entry z = x.right.right;
        E e = x.element;
        x.element = z.element;
        z.element = e;

        //change relations for "A,B,C,D"
        y.right = z.left;
        if(y.right != null){
            y.right.parent = y;
        }
        x.right = z.right;
        if(x.right != null){
            x.right.parent = x;
        }
        z.right = y.left;
        if(z.right != null){
            z.right.parent = z;
        }
        z.left = x.left;
        if(z.left != null){
            z.left.parent = z;
        }

        //change relations for x,y,z
        x.left = y;
        //
        y.left = z;
    }

    @Override
    public E get(E e) {
        Entry entry = find(e, root);
        return entry == null ? null : entry.element;
    }
}
