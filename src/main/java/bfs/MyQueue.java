package bfs;

import java.util.ArrayList;
import java.util.List;

public class MyQueue<K> {
    private List queue;

    public MyQueue() {
        queue = new ArrayList<K>();
    }

    public void push(K element) {
        if (element != null) {
            queue.add(element);
        } else {
            throw new IllegalArgumentException("Element can't be null");
        }
    }

    public K pop() {
        return (K) queue.remove(0);
    }

    public boolean isEmpty() {
        return queue.size() == 0;
    }
}
