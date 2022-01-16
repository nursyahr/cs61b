package deque;

/*
front = the empty front
back = the empty back
filled front = front-1
filled back = back + 1
 */

public class ArrayDeque<T> {
    private int size;
    private int front;
    private int back;
    private T[] items;
    private int arrlength;
     // double when full, halve when 0.25 full



    public ArrayDeque(){
        items = (T[]) new Object[8]; // cannot instantiate a generic array because it will be e.g. String = Object cast
        size = 0;
        arrlength = items.length;
        front = back = 0;
    }
    // After every add or remove, check if necessary to resize;
    private void checkresize(){
        if ( (back+1) %arrlength == front) {    // resize when it's filled
            resize(size * 2);
        }

       double ur = ((double) size) / ((double) arrlength);
        if (arrlength > 50 && ur <= 0.25){
            resize((int)Math.round(size * 0.5)); // math.round returns a double
        }
    }

    private void resize(int newSize){
        T[] newArr = (T[]) new Object[newSize];
        int i = 0;
        int j = front;
        while (j <= back){
            newArr[i] = items[j];
            j = (j + 1) % arrlength;
            i += 1;
            if (newArr[0] != null & j == 0){
                break;
            }
        }
        items = newArr;
        arrlength = items.length;
    }

    public void addFirst(T item) {
        items[front] = item;
        size += 1;
        checkresize();
        front = Math.floorMod((front-1), arrlength); // update new front;
    }

    public void addLast(T item){
        items[back] = item;
        size += 1;
        checkresize();
        back = (back + 1) % arrlength; //update new back
    }

    public T removeFirst(){
        if(this.isEmpty()) {
            return null;
        } else {
            T removed = items[(front + 1) % arrlength];
            front = (front + 1) % arrlength;
            size -= 1;
            return removed;
        }
    }

    public T removeLast(){
        if(this.isEmpty()) {
            return null;
        } else {
            T removed = items[(back - 1) % arrlength];
            back = (back - 1) % arrlength;
            size -= 1;
            return removed;
        }
    }

    public int size(){return size;}

    public boolean isEmpty(){ return size == 0; }

    public T get(int index) {
        if (index < size) {
            int i = (index + front) % arrlength;
            return items[i];
        }
        return null;
    }
}
