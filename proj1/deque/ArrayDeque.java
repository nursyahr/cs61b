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
    private int ur; // double when full, halve when 0.25 full
    int arrlength;




    public ArrayDeque(){
        items = (T[]) new Object[8]; // cannot instantiate a generic array because it will be e.g. String = Object cast
        size = 0;
        arrlength = items.length;
        ur = size/arrlength;
        front = back = 0;

    }
    // After every add or remove, check if necessary to resize;
    public void checkresize(){
        if (front == back) {
            resize(size * 2);
        }

        if (ur <= 0.25){
            resize((int)Math.round(size * 0.5)); // math.round returns a double
        }
    }

    private void resize(int newSize){
        T[] newArr = (T[]) new Object[newSize];
        int i = 0;
        int j = front;
        while (j != front){
            newArr[i++] = items[j];
            j = (j + 1) % arrlength;
        }
        items = newArr;
    }

    public void addFirst(T item) {
        items[front] = item;
        size += 1;
        front = (front -1) % arrlength; // update new front;
        checkresize();
    }

    public void addLast(T item){
        items[back] = item;
        size -= 1;
        back = (back + 1) % arrlength; //update new back
        checkresize();
    }

    public T removeFirst(){
        if(this.isEmpty()) {
            return null;
        } else {
            T removed = items[front-1];
            front = (front - 1) % arrlength;
            size -= 1;
            return removed;
        }
    }

    public T removeLast(){
        if(this.isEmpty()) {
            return null;
        } else {
            T removed = items[back + 1];
            back = (back + 1) % arrlength;
            size -= 1;
            return removed;
        }
    }

    public int size(){return size;}

    public boolean isEmpty(){ return size == 0; }

    public T get(int index){
        int i = (index + front) % arrlength;
        return items[i];
        }
}
