package net.daichang.dcmods.utils.helpers;

import java.util.Random;

public class MathHelper {
    private static final Random random = new Random();

    // 随机整数
    public static int getRandomInt(int min, int max) {
        if (min > max) throw new IllegalArgumentException("max must be greater than or equal to min");
        return random.nextInt(max - min + 1) + min;
    }

    //随机双精度浮点数
    public static double getRandomDouble(double min, double max) {
        if (min > max) throw new IllegalArgumentException("max must be greater than or equal to min");
        return min + (max - min) * random.nextDouble();
    }

    //随机余弦值(以弧度为单位)
    public static double getCosine(double angle) {
        return Math.cos(angle);
    }

    //计算余弦值（以度为单位）
    public static double getCosineInDegrees(double angleInDegrees) {
        return Math.cos(Math.toRadians(angleInDegrees));
    }

    //正弦值（弧度）
    public static double getSine(double angle) {
        return Math.sin(angle);
    }

    //正弦值（度）
    public static double getSineInDegrees(double angleInDegrees) {
        return Math.sin(Math.toRadians(angleInDegrees));
    }

    //指定数的平方根
    public static double getSquareRoot(double number) {
        if (number < 0) throw new IllegalArgumentException("number must be non-negative");
        return Math.sqrt(number);
    }

    //指定数的幂
    public static double getPower(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    //给定数的绝对值
    public static double getAbsoluteValue(double number) {
        return Math.abs(number);
    }

    //指定整数的绝对值
    public static int getAbsoluteValue(int number) {
        return Math.abs(number);
    }

    // 计算最大值
    public static double getMax(double... numbers) {
        if (numbers == null || numbers.length == 0) throw new IllegalArgumentException("At least one number must be provided");
        double max = numbers[0];
        for (double num : numbers) {
            if (num > max) max = num;
        }
        return max;
    }

    // 计算最小值
    public static double getMin(double... numbers) {
        if (numbers == null || numbers.length == 0) throw new IllegalArgumentException("At least one number must be provided");
        double min = numbers[0];
        for (double num : numbers) {
            if (num < min) min = num;
        }
        return min;
    }

    // 计算自然对数
    public static double getNaturalLog(double number) {
        if (number <= 0) throw new IllegalArgumentException("number must be positive");
        return Math.log(number);
    }

    // 计算常用对数 (以10为底)
    public static double getCommonLog(double number) {
        if (number <= 0) throw new IllegalArgumentException("number must be positive");
        return Math.log10(number);
    }

    // 计算阶乘
    public static long getFactorial(int number) {
        if (number < 0) throw new IllegalArgumentException("number must be non-negative");
        long result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }

    // 判断素数
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) return false;
        }
        return true;
    }

    // 计算圆周率 (π) 的值
    public static double getPi() {
        return Math.PI;
    }

    // 计算欧拉数 (e) 的值
    public static double getE() {
        return Math.E;
    }

    // 计算正切值（弧度）
    public static double getTangent(double angle) {
        return Math.tan(angle);
    }

    // 计算正切值（度）
    public static double getTangentInDegrees(double angleInDegrees) {
        return Math.tan(Math.toRadians(angleInDegrees));
    }

    // 计算反正弦值（弧度）
    public static double getArcSine(double angle) {
        if (angle < -1 || angle > 1) throw new IllegalArgumentException("angle must be between -1 and 1");
        return Math.asin(angle);
    }

    // 计算反正弦值（度）
    public static double getArcSineInDegrees(double angle) {
        if (angle < -1 || angle > 1) throw new IllegalArgumentException("angle must be between -1 and 1");
        return Math.toDegrees(Math.asin(angle));
    }

    // 计算反余弦值（弧度）
    public static double getArcCosine(double angle) {
        if (angle < -1 || angle > 1) throw new IllegalArgumentException("angle must be between -1 and 1");
        return Math.acos(angle);
    }

    // 计算反余弦值（度）
    public static double getArcCosineInDegrees(double angle) {
        if (angle < -1 || angle > 1) throw new IllegalArgumentException("angle must be between -1 and 1");
        return Math.toDegrees(Math.acos(angle));
    }

    // 计算反正切值（弧度）
    public static double getArcTangent(double angle) {
        return Math.atan(angle);
    }

    // 计算反正切值（度）
    public static double getArcTangentInDegrees(double angle) {
        return Math.toDegrees(Math.atan(angle));
    }

    // 计算两个数的和
    public static double getSum(double... numbers) {
        if (numbers == null) throw new IllegalArgumentException("numbers must not be null");
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum;
    }

    // 计算两个数的平均值
    public static double getAverage(double... numbers) {
        if (numbers == null || numbers.length == 0) throw new IllegalArgumentException("At least one number must be provided");
        return getSum(numbers) / numbers.length;
    }

    // 计算两个数的差
    public static double getDifference(double a, double b) {
        return a - b;
    }

    // 计算两个数的积
    public static double getProduct(double... numbers) {
        if (numbers == null) throw new IllegalArgumentException("numbers must not be null");
        double product = 1;
        for (double num : numbers) {
            product *= num;
        }
        return product;
    }

    // 计算两个数的商
    public static double getQuotient(double a, double b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return a / b;
    }

    // 计算两个数的模
    public static double getModulus(double a, double b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return a % b;
    }

    // 计算向量的模
    public static double getVectorMagnitude(double... components) {
        if (components == null) throw new IllegalArgumentException("components must not be null");
        double sumOfSquares = 0;
        for (double component : components) {
            sumOfSquares += component * component;
        }
        return Math.sqrt(sumOfSquares);
    }

    // 计算两个向量的点积
    public static double getDotProduct(double[] vector1, double[] vector2) {
        if (vector1 == null || vector2 == null) throw new IllegalArgumentException("Vectors must not be null");
        if (vector1.length != vector2.length) throw new IllegalArgumentException("Vectors must have the same length");
        double dotProduct = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }
        return dotProduct;
    }

    // 计算两个向量的叉积（仅限三维向量）
    public static double[] getCrossProduct(double[] vector1, double[] vector2) {
        if (vector1 == null || vector2 == null) throw new IllegalArgumentException("Vectors must not be null");
        if (vector1.length != 3 || vector2.length != 3) throw new IllegalArgumentException("Vectors must be 3-dimensional");
        double[] crossProduct = new double[3];
        crossProduct[0] = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        crossProduct[1] = vector1[2] * vector2[0] - vector1[0] * vector2[2];
        crossProduct[2] = vector1[0] * vector2[1] - vector1[1] * vector2[0];
        return crossProduct;
    }
}
