package utils;

public class Vector {
    //Default values
    public static final Vector ZERO = new Vector(0, 0);
    public static final Vector UP = new Vector(0, -1);
    public static final Vector LEFT = new Vector(-1, 0);
    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector RIGHT = new Vector(1, 0);

    //Coordinates of the vector
    public float x;
    public float y;

    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }

    //Length of the vector
    public float magnitude(){
        return (float)Math.sqrt(x*x + y*y);
    }

    //Make the vector into a 1 unit long vector
    public Vector normalized(){
        float magnitude = magnitude();
        return magnitude == 0 ? this : new Vector(x / magnitude, y / magnitude);
    }

    //Clamp the length of the vector
    public void clampMagnitude(float maxMagnitude){
        if (magnitude() > maxMagnitude){
            Vector newVector = normalized();
            x = newVector.x * maxMagnitude;
            y = newVector.y * maxMagnitude;
        }
    }

    //Calculate the angle of the vector
    public float angle(){

        return (float) Math.atan2(y, x);
    }

    @Override
    public String toString() {

        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        Vector other = (Vector) obj;
        return x == other.x && y == other.y;
    }
}
