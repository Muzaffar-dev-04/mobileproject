import uz.pdp.beck.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        //ArrayList<User> users = new ArrayList<>();
        Car car = new Car("mers");
        Car car1 = new Car("bmw");

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car);
        //List<Car> cars = new ArrayList<>();
        //cars = read();
        write(cars);

        List<Car> carList = read();
        for (Car car2 : carList) {
            System.out.println(car2.name);
        }
    }

    private static void write(List<Car> cars) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("repository/cars.txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ){
            objectOutputStream.writeObject(cars);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Car> read() {
        try (
                FileInputStream fileInputStream = new FileInputStream("repository/cars.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ){
            ArrayList<Car> cars = (ArrayList<Car>) objectInputStream.readObject();
            if (cars == null)   cars = new ArrayList<>();
            return cars;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

class Car implements Serializable {
    String name;

    public Car(String name) {
        this.name = name;
    }
}
