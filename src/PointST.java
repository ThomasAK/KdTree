import edu.princeton.cs.algs4.*;

public class PointST<Value> {

    private RedBlackBST<Point2D, Value> table;

    public PointST(){
        table = new RedBlackBST<>();
    }

    public boolean isEmpty(){
        return table.isEmpty();
    }

    public int size(){
        return table.size();
    }

    public void put(Point2D p, Value val){
        if(p == null || val == null){
            throw new NullPointerException();
        }
        table.put(p,val);
    }

    public Value get(Point2D p){
        if(p==null)throw new NullPointerException();
        return table.get(p);
    }

    public boolean contains(Point2D p){
        if(p==null)throw new NullPointerException();
        return get(p) != null;
    }

    public Iterable<Point2D> points(){
        return table.keys();
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null)throw new NullPointerException();
        Queue<Point2D> queue = new Queue<>();
        for(Point2D p: table.keys()) {
            if(rect.contains(p)) queue.enqueue(p);
        }
        return queue;
    }

    public Point2D nearest(Point2D p){
        if(p==null)throw new NullPointerException();
        Point2D closest = null;
        for(Point2D point: table.keys()){
            if(closest == null || point.distanceSquaredTo(p)<closest.distanceSquaredTo(p)){
                closest = point;
            }
        }
        return closest;


    }

    public static void main(String[] args) {

        //test constructor, insert, and draw
        PointST<Integer> points = new PointST<Integer>();
        System.out.println(points.isEmpty());
        for (int i = 0; i < 100; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points.put(new Point2D(x, y),1);
        }
        System.out.println(points.isEmpty());
        //test range
        RectHV rect = new RectHV(20, 20, 40, 40);
        Iterable<Point2D> list = points.range(rect);
        System.out.println("Points inside of: " + rect);
        for(Point2D key: list) {
            System.out.println(key);
        }//end loop

        //test nearest
        Point2D point = new Point2D(50, 50);
        System.out.println("\nNearest point to " + point);
        System.out.println(points.nearest(point));
        System.out.println("\nAll Points");
        for(Point2D p : points.points()){
            System.out.println(p);
        }


    }//end main


}
