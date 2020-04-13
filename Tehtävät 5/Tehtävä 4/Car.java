import java.util.ArrayList;
import java.util.List;

class Car {

  public Car() {
    List<Object> parts = new ArrayList<Object>();
    Body body = new Body();
    parts.add(body);
    Chassis chassis = new Chassis();
    parts.add(chassis);
    Engine engine = new Engine();
    parts.add(engine);
    Wheel wheel;
    for (int kierros = 0; kierros < 4; kierros++) {
      wheel = new Wheel();
      parts.add(wheel);
    }
    System.out.println("Car parts:");
    String type = "";
    String previousType = "0";
    Integer count = 1;
    for (Object part : parts) {
      type = part.getClass().getName();
      if (previousType == "0") {
        previousType = type;
        continue;
      }
      if (type == previousType) {
        count++;
      } else {
        print(count, type, previousType);
        count = 1;
      }
      previousType = type;
    }
    print(count, type, previousType);
  }

  private void print(Integer count, String type, String previousType) {
    if (count != 1) {
      System.out.println("	" + count.toString() + " " + type);
    } else if (type != previousType) {
      System.out.println("	" + previousType);
    }
  }
}

class Body {

  public Body() {
    System.out.println("Manufacturing: Body");
  }
}

class Chassis {

  public Chassis() {
    System.out.println("Manufacturing: Chassis");
  }
}

class Engine {

  public Engine() {
    System.out.println("Manufacturing: Engine");
  }
}

class Wheel {

  public Wheel() {
    System.out.println("Manufacturing: Wheel");
  }
}
