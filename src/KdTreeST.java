import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class KdTreeST<Value> {
    private Node root;             // root of BST
    private int size;

    private class Node {
        private Point2D key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Point2D key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public KdTreeST() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void put(Point2D p, Value val) {
        if (p == null || val == null) {
            throw new NullPointerException();
        }
        size++;
        if (root == null) {
            root = new Node(p, val, size);
            return;
        }
        put(p, val, 0, root);

    }

    private void put(Point2D p, Value val, int level, Node n) {
        if (level % 2 == 0) {
            if (p.x() > n.key.x()) {
                if (n.right == null) {
                    n.right = new Node(p, val, size);
                } else {
                    put(p, val, ++level, n.right);
                }
            } else {
                if (n.left == null) {
                    n.left = new Node(p, val, size);
                } else {
                    put(p, val, ++level, n.left);
                }
            }
        } else {
            if (p.y() > n.key.y()) {
                if (n.right == null) {
                    n.right = new Node(p, val, size);
                } else {
                    put(p, val, ++level, n.right);
                }
            } else {
                if (n.left == null) {
                    n.left = new Node(p, val, size);
                } else {
                    put(p, val, ++level, n.left);
                }
            }

        }

    }

    public Value get(Point2D p) {
        if (p == null) throw new NullPointerException();
        return get(p, 0, root);
    }

    private Value get(Point2D p, int level, Node n) {
        if (n.key.equals(p)) return n.val;
        if (level % 2 == 0) {
            if (p.x() > n.key.x()) {
                get(p, ++level, n.right);
            } else {
                get(p, ++level, n.left);
            }
        } else {
            if (p.y() > n.key.y()) {
                get(p, ++level, n.right);
            } else {
                get(p, ++level, n.left);
            }
        }
        return null;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return get(p) != null;
    }

    public Iterable<Point2D> points() {
        if (root == null) return null;
        List<Point2D> result = new ArrayList<>();
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node node = nodes.remove();
            result.add(node.key);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right != null) {
               nodes.add(node.right);
            }
        }
        return result;
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null)throw new NullPointerException();
        Queue<Point2D> queue = new Queue<>();
        for(Point2D p: points()) {
            if(rect.contains(p)) queue.enqueue(p);
        }
        return queue;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Point2D closest = null;
        for (Point2D point : points()) {
            if (closest == null || point.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
                closest = point;
            }
        }
        return closest;


    }

    public static void main(String[] args) {

        //test constructor, insert, and draw
        KdTreeST<Integer> points = new KdTreeST<>();
        System.out.println(points.isEmpty());
        for (int i = 0; i < 100; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points.put(new Point2D(x, y), 1);
        }
        System.out.println(points.isEmpty());
        //test range
        RectHV rect = new RectHV(20, 20, 40, 40);
        Iterable<Point2D> list = points.range(rect);
        System.out.println("Points inside of: " + rect);
        for (Point2D key : list) {
            System.out.println(key);
        }//end loop

        //test nearest
        Point2D point = new Point2D(50, 50);
        System.out.println("\nNearest point to " + point);
        System.out.println(points.nearest(point));
        System.out.println("\nAll Points");
        for (Point2D p : points.points()) {
            System.out.println(p);
        }


    }//end main

}
