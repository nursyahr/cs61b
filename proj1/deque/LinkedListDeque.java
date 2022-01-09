package deque;
public class LinkedListDeque<T>{
    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        public IntNode(T i) {
            item = i;
        }
    }

    private IntNode sentinel;
    private int size = 0;

    public LinkedListDeque(){
        sentinel = new IntNode(null);
        sentinel.next = new IntNode(null);
        sentinel.prev = new IntNode(null);
    }

    public void addFirst(T item){
        // addFirst to queue//
        IntNode newFirst = new IntNode(item);
        newFirst.prev = sentinel;
        newFirst.next = sentinel.next;
        sentinel.next.prev = newFirst;
        sentinel.next = newFirst;
        if (this.isEmpty()) {           // if the DLList is empty, need to establish the prev and next
            sentinel.prev = newFirst;
            newFirst.next = sentinel;
        }
        this.size += 1;
    }

    public void addLast(T item){
        IntNode newLast = new IntNode(item);
        newLast.prev = sentinel.prev;
        newLast.next = sentinel;
        sentinel.prev.next = newLast;
        sentinel.prev = newLast;
        if (this.isEmpty()) {            // if the DLList is empty, need to establish the prev and next
            newLast.prev = sentinel;
            sentinel.next = newLast;
        }
        this.size += 1;
    }

    public int size(){
        return this.size;
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

    public T getRecursive(int index){
        return null;
    }



}