package deque;
public class LinkedListDeque<T>{
    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        public IntNode(T i, IntNode n, IntNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private IntNode sentinel;
    private int size = 0;

    public void addFirst(T item){
        // addFirst to queue//
        IntNode oldNext = this.sentinel.next;
        IntNode newNode = new IntNode(item, oldNext, this.sentinel); // add to front
        this.sentinel.next = newNode;
        oldNext.prev = newNode;
        this.size += 1;
    }

    public void addLast(T item){
        IntNode oldPrev = this.sentinel.prev;
        IntNode newNode = new IntNode(item, this.sentinel, oldPrev); // add to last
        this.sentinel.prev = newNode;
        oldPrev.next = newNode;
        size += 1;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){ return size == 0; }

    public T removeFirst(){
        if(this.isEmpty()) {
            return null;
        } else {
            T returnedItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return returnedItem;
        }
    }

    public T removeLast(){
        if(this.isEmpty()) {
            return null;
        } else {
            T returnedItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return returnedItem;
        }
    }

    public T get(int index){ // i = 2; index = 2; 2 < 2 +1;
        if (index < size){
            int i = 0;
            T returnItem;
            IntNode p = sentinel.next; // start with the first item sentinel 2 3 4 size = 3
            while (i < index + 1){
                p = p.next;
                i += 1;
            }
            returnItem = p.item;
            return returnItem;
        }
        return null;
    }

}