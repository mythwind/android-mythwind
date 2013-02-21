package test.mogo;

public class Point {  
    
    public int x;  
    public int y;  
      
      
    public Point(int x, int y){  
        this.x = x;  
        this.y = y;  
    }  
      
    public int[] getPoint(){  
        int[] point = new int[2];  
        point[0] = x;  
        point[1] = y;  
          
        return point;  
    }  
      
    public String toString(){  
          
        return new StringBuilder("[").append(x).append(",").append(y).append("]").toString();  
    }  
  
}  

