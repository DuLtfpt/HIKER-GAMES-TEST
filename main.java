import java.math.BigDecimal;
class Wrapper<T> {
    private T x;
    private T y;

    public Wrapper(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Wrapper() {
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }
}
public class main {
    public static void main(String[] args) {
        System.out.println("---------------start---------------");

        /*----------------------- logic code to be executed ----------------------------*/
        // Prime number
        System.out.println("\n\n------------Prime number-----------");
        long number = 1954;
        System.out.println(number+" is prime number?: "+checkPrimeNumber(number));

        // Fibonacci
        System.out.println("\n\n-------------Fibonacci-------------");
        System.out.println("First 100 Fibonacci numbers");
        checkFibonacci(100);

        // Fraction calculation
        System.out.println("\n\n--------Fraction calculation--------");
        String f1 = "21/20";
        String f2 = "41/-20";
        System.out.print(f1+ " + "+ f2 +"=");
        System.out.println(calculate(f1, f2));

        // Calculate Coordinates
        System.out.println("\n\n--------Calculate Coordinates-------");
        double v0 = 30;
        // angle in degree
        double a = 60;
        Wrapper result;
        for (double t = 0; t < 10; t += 0.25) {
            result = calculateCoordinates(t, v0, a);
            System.out.printf("v0= %.2f a(degree)= %.2f t= %.2f  (x= %.2f y= %.2f) \n"
                    ,v0,a,t,result.getX(),result.getY());
        }


        /*------------------------------------ end --------------------------------------*/
        System.out.println("----------------end----------------");
    }


    /*------------------------------------ Q1 --------------------------------------*/
    public static boolean checkPrimeNumber(long number) {
        // Prime number not equal to 1 or 0 or negative number
        if (number <= 1) {
            return false;
        }

        long i=2;
        // This will loop from 2 to int(sqrt(x))
        while ((i*i)<number){
            //if found a divisor
            if (number % i == 0) {
                return false;
            }
            i++;
        }
        // If not found any divisor then number is prime number
        return true;
    }
    /*---------------------------------- End Q1 ------------------------------------*/


    /*------------------------------------ Q2 --------------------------------------*/
    public static void checkFibonacci(int n) {
        int i = 0;
        BigDecimal f0 = new BigDecimal(0);
        BigDecimal f1 = new BigDecimal(1);
        BigDecimal fn;

        // print initial values
        System.out.println(f0);
        System.out.println(f1);

        // Loop until reach 100 numbers
        // Not include 2 initial value
        n -= 2;
        while (i < n) {
            fn = f0.add(f1);
            // Print the number
            System.out.println(fn);

            // Swap
            f0 = f1;
            f1 = fn;
            i++;
        }
    }
    /*---------------------------------- End Q2 ------------------------------------*/


    /*------------------------------------ Q3 --------------------------------------*/
    public static String calculate(String f1, String f2) {

        // Check format input by format x/y
        // x is a number and y is a number not 0
        if (!f1.matches("^-{0,1}\\d+/-{0,1}[1-9]\\d*$")) {
            return "Invalid input: " + f1;
        }
        if (!f2.matches("^-{0,1}\\d+/-{0,1}[1-9]\\d*$")) {
            return "Invalid input:" + f2;
        }

        // Split and parse to long
        String[] ab = f1.split("/");
        String[] cd = f2.split("/");
        long a = Long.parseLong(ab[0]);
        long b = Long.parseLong(ab[1]);
        long c = Long.parseLong(cd[0]);
        long d = Long.parseLong(cd[1]);

        //normalize fraction
        Wrapper<Long> n1 = normalizeFraction(a, b);
        Wrapper<Long> n2 = normalizeFraction(c, d);
        a = n1.getX();
        b = n1.getY();
        c = n2.getX();
        d = n2.getY();

        // add
        long f = (b * d) / calGCD(b, d);
        long e = a * (f / b) + c * (f / d);

        //normalize fraction
        Wrapper<Long> rs = normalizeFraction(e, f);
        e = rs.getX();
        f = rs.getY();

//        if (b == 1) {
//            return String.valueOf(a);
//        }

        return e + "/" + f;
    }


    public static Wrapper<Long> normalizeFraction(long x, long y) {
        //normalize fraction case -x/-y or x/-y
        if ((x < 0 && y < 0) || (x > 0 && y < 0)) {
            x = -x;
            y = -y;
        }

        //irreducible fraction
        long gdcValue = calGCD(x, y);
        x = x / gdcValue;
        y = y / gdcValue;

        return new Wrapper<Long>(x, y);
    }

    public static long calGCD(long a, long b) {
        long r = a % b;
        while (r != 0) {
            a = b;
            b = r;
            r = a % b;
        }
        return b;
    }
    /*---------------------------------- End Q3 ------------------------------------*/


    /*------------------------------------ Q4 --------------------------------------*/
    public static Wrapper<Double> calculateCoordinates(double t, double v0, double a) {
        double g = 9.8;
        double sina = Math.sin(Math.toRadians(a));
        double powT = Math.pow(t, 2);

        // H = (v0^2 * sin(a)^2) / 2g
        double H = (Math.pow(v0, 2) * Math.pow(sina, 2)) / (2 * g);
        // t1= v0 * (sin(a) / g )
        double t1 = v0 * (sina / g);
        // t2^2 for calculate downward state
        double powT2 = Math.pow(t - t1, 2);
        // x = v0 * cos(a) * t
        double x = v0 * Math.cos(Math.toRadians(a)) * t;

        Wrapper<Double> point = new Wrapper<>();
        point.setX(x);

        // downward state happened when t greater than t1
        if (t > t1) {
            // downward state
            // y = H - (g * powT2)/2
            point.setY(H - 0.5 * g * powT2);
        } else {
            // upward state
            // y = v0 * sin(a) * t - (g * powT)/2
            point.setY(v0 * sina * t - 0.5 * g * powT);
        }
        return point;
    }
    /*---------------------------------- End Q4 ------------------------------------*/
}
