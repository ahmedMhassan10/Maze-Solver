import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.Scanner;
interface IMazeSolver
{
    public int[][] solveBFS(java.io.File maze);
    public int[][] solveDFS(java.io.File maze);
}
class ArrayQueue
{
    int left = 0, right = 0;
    Object [] arr = new Object[1000000];

    public ArrayQueue() {};
    public ArrayQueue(int [] array)
    {
        for (int i= array.length - 1; i >= 0; i--)
            this.enqueue(array[i]);
    }
    public void enqueue(Object item)
    {
        arr[right++] = item;
    }
    public Object dequeue()
    {
        if (this.isEmpty())
        {
            System.out.print("Error");
            System.exit(0);
        }
        return arr[left++];
    }
    public boolean isEmpty() {return size() == 0;}
    public int size() {return right - left;}
    public void print()
    {
        Object[] arra = new Object[this.size()];

        int i = this.size() - 1;
        while (!this.isEmpty())
        {
            arra[i] = this.dequeue();
            i--;
        }
        System.out.print("[");
        for (i = 0; i < arra.length; i++)
        {
            System.out.print(arra[i]);
            if (i != arra.length - 1)
                System.out.print(", ");
        }
        System.out.print("]");
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String sss = sc.nextLine();
        String sin = sss.replaceAll("\\[|\\]", "");
        String[] s = sin.split(", ");;
        int[] arr = new int[s.length];
        if (s.length == 1 && s[0].isEmpty())
            arr = new int[]{};
        else
        {
            for(int i = 0; i < s.length; ++i)
                arr[i] = Integer.parseInt(s[i]);
        }

        ArrayQueue q = new ArrayQueue();
        if (sss.length() != 2)
            q = new ArrayQueue(arr);
        else
            q = new ArrayQueue();
        String str = sc.nextLine();
        if (str.equals( "enqueue"))
        {
            int e = sc.nextInt();
            q.enqueue(e);
            q.print();
        }
        else if (str.equals( "dequeue"))
        {
            q.dequeue();
            q.print();
        }
        else if (str.equals( "isEmpty"))
        {
            if (q.isEmpty())
                System.out.println("True");
            else
                System.out.println("False");
        }
        else if (str.equals( "size"))
        {
            System.out.print(q.size());
        }
        else
            System.out.println("Error");
    }

}
class MyStack
{
    int _size = 0;
    Object [] arr = new Object[1000000];

    public Object pop()
    {
        if (_size == 0)
        {
            System.out.print("Error");
            System.exit(0);
        }
        return arr[--_size];
    }

    public Object peek()
    {
        if (_size == 0)
        {
            System.out.print("Error");
            System.exit(0);
        }
        return arr[_size - 1];
    }

    public void push(Object element)
    {
        arr[_size++] = element;
    }

    public boolean isEmpty()
    {
        return _size == 0;
    }

    public int size()
    {
        return _size;
    }

    public static void main(String[] args)
    {
        MyStack stack = new MyStack();

        Scanner sc = new Scanner(System.in);
        String sin = sc.nextLine().replaceAll("\\[|\\]", "");
        String[] s = sin.split(", ");;
        int[] arr = new int[s.length];
        if (s.length == 1 && s[0].isEmpty())
            arr = new int[]{};
        else
        {
            for(int i = 0; i < s.length; ++i)
                arr[i] = Integer.parseInt(s[i]);
        }
        for (int i = 0; i < arr.length; i++)
            stack.push(arr[arr.length - i - 1]);

        String s2 = sc.nextLine();

        if (s2.equals("push"))
        {
            int in = sc.nextInt();
            stack.push(in);
        }
        else if (s2.equals("pop"))
        {
            stack.pop();
        }
        else if (s2.equals("peek"))
        {
            System.out.print(stack.peek());
            System.exit(0);
        }
        else if (s2.equals("isEmpty"))
        {
            if (stack.isEmpty())
                System.out.print("True");
            else
                System.out.print("False");
            System.exit(0);
        }
        else if (s2.equals("size"))
        {
            System.out.print(stack.size());
            System.exit(0);
        }
        else
        {
            System.out.print("Error");
            System.exit(0);
        }
        System.out.print("[");
        int ii = 0;
        while (!stack.isEmpty())
        {
            System.out.print(stack.pop());
            if (stack.size() != 0)
                System.out.print(", ");
        }
        System.out.print("]");
    }
}
public class MazeSolver implements IMazeSolver
{
    int m,n,sx,sy,counter=1;
    int ex,ey,reached=0,current_nodes=1,next_nodes=0;
    String[] arr;


    public int[][] solveBFS(java.io.File maze)
    {
        try {
            Scanner sc = new Scanner(maze);
            String str = sc.nextLine();
            String[] s = str.split(" ");
            m = Integer.parseInt(s[0]);
            n = Integer.parseInt(s[1]);
            arr = new String[m];
            for (int i = 0; i < m; i++) {
                str = sc.nextLine();
                arr[i] = str;
                System.out.println(arr[i]);
            }
            sc.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("Maze.txt not found..!");
        }

        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(arr[i].charAt(j)=='S'){
                    sx=i;
                    sy=j;
                } else if (arr[i].charAt(j)=='E') {
                    ex= i;
                    ey=j;
                }
            }
        }
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                visited[i][j] = false;

        Point[][] parents = new Point[m][n];
        ArrayQueue q = new ArrayQueue();
        ArrayQueue previous = new ArrayQueue();

        q.enqueue(new Point(sx, sy));
        previous.enqueue(new Point(-1, -1));
        boolean done = false;

        while(!q.isEmpty())
        {

            Point current = (Point)q.dequeue();
            int x = current.x, y = current.y;
            Point prev = (Point)previous.dequeue();
            if (x < 0 || y < 0 || x >= m || y >= n)
                continue;
            if (arr[x].charAt(y) == '#')
                continue;
            if (visited[x][y])
                continue;
            visited[x][y] = true;
            parents[x][y] = prev;

            if (x == ex && y == ey)
            {
                done = true;
                break;
            }
            q.enqueue(new Point(x - 1, y));
            q.enqueue(new Point(x + 1, y));
            q.enqueue(new Point(x, y - 1));
            q.enqueue(new Point(x, y + 1));

            for (int i = 0; i < 4; i++)
                previous.enqueue(new Point(x, y));
        }

        if (!done)
        {
            System.out.print("NOT FOOUND");
            System.exit(0);
        }

        Point[] cities = new Point[100000];
        int size = 0;
        int x = ex, y = ey;
        while (true)
        {
            cities[size++] = new Point(x, y);
            if (x == sx && y == sy)
                break;
            int v1 = parents[x][y].x;
            int v2 = parents[x][y].y;
            x = v1; y = v2;
        }
        for (int i = 0; i < size / 2; i++)
        {
            Point pp = cities[i];
            cities[i] = cities[size - i - 1];
            cities[size - i - 1] = pp;
        }
        int [][] array = new int[size][2];
        for (int i = 0; i < size; i++)
        {
            array[i][0] = cities[i].x; array[i][1] = cities[i].y;
        }
        return array;
    }

    public int[][] solveDFS(java.io.File maze)
    {
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                visited[i][j] = false;

        Point[][] parents = new Point[m][n];
        MyStack q = new MyStack();
        MyStack previous = new MyStack();

        q.push(new Point(sx, sy));
        previous.push(new Point(-1, -1));
        boolean done = false;
        while(true)
        {
            Point current = (Point)q.pop();
            int x = current.x, y = current.y;
            Point prev = (Point)previous.pop();
            if (x < 0 || y < 0 || x >= m || y >= n)
                continue;
            if (arr[x].charAt(y) == '#')
                continue;
            if (visited[x][y])
                continue;
            visited[x][y] = true;
            parents[x][y] = prev;

            if (x == ex && y == ey)
            {
                done = true;
                break;
            }
            q.push(new Point(x - 1, y));
            q.push(new Point(x + 1, y));
            q.push(new Point(x, y - 1));
            q.push(new Point(x, y + 1));

            for (int i = 0; i < 4; i++)
                previous.push(new Point(x, y));
        }

        if (!done)
        {
            System.out.print("NOT FOOUND");
            System.exit(0);
        }

        Point[] cities = new Point[100000];
        int size = 0;
        int x = ex, y = ey;
        while (true)
        {
            cities[size++] = new Point(x, y);
            if (x == sx && y == sy)
                break;
            int v1 = parents[x][y].x;
            int v2 = parents[x][y].y;
            x = v1; y = v2;
        }
        for (int i = 0; i < size / 2; i++)
        {
            Point pp = cities[i];
            cities[i] = cities[size - i - 1];
            cities[size - i - 1] = pp;
        }
        int [][] array = new int[size][2];
        for (int i = 0; i < size; i++)
        {
            array[i][0] = cities[i].x; array[i][1] = cities[i].y;
        }
        return array;
    }


    public static void main(String args[]){
        MazeSolver s = new MazeSolver();
        String file_name = "maze10.txt";
        File file = new File(file_name);

        int[][] bfs = s.solveBFS(file);

        for(int i=0;i<bfs.length-1;i++) {
            System.out.print("("+bfs[i][0]+" ,"+bfs[i][1]+")"+" ,");
        }
        System.out.println("("+s.ex+" ,"+s.ey+")");
        int[][] dfs = s.solveDFS(file);
        for(int i=0;i<dfs.length-1;i++) {
            System.out.print("("+dfs[i][0]+" ,"+dfs[i][1]+")"+" ,");
        }
        System.out.println("("+s.ex+" ,"+s.ey+")");
    }
}