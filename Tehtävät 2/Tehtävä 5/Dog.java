import java.util.Scanner;

class Dog {
  private String name;

  public Dog(String newName) {
    if (newName.trim().isEmpty()) {
      name = "Doge";
    } else {
      name = newName;
    }
    System.out.println("Hey, my name is " + name);
  }

  public boolean speak(String newBark) {
    if (newBark.trim().isEmpty()) {
      System.out.println("Much wow!");
      return true;
    }

    Scanner barkScan = new Scanner(newBark);
    while (barkScan.hasNext()) {
      if (barkScan.hasNextBoolean()) {
        Boolean value = barkScan.nextBoolean();
        System.out.println("Such boolean: " + value);
      } else if (barkScan.hasNextInt()) {
        Integer value = barkScan.nextInt();
        System.out.println("Such integer: " + value);
      } else {
        System.out.println(barkScan.next());
      }
    }
    barkScan.close();
    return false;
  }
}
