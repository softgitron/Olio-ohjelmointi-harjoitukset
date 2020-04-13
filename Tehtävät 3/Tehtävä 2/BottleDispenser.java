import java.util.ArrayList;

public class BottleDispenser {
  // The array for the Bottle-objects
  private ArrayList<Bottle> bottleArray = new ArrayList<Bottle>();

  private float money;

  public BottleDispenser(float initialMoney) {
    money = initialMoney;
  }

  public void addBottle(Bottle bottle) {
    bottleArray.add(bottle);
  }

  public void addMoney() {
    money += 1;

    System.out.println("Klink! Added more money!");
  }

  public void buyBottle(int choice) {
    if (choice > bottleArray.size() ) {
      System.out.println("Outs! No such bottle");
    } else if (money - bottleArray.get(choice - 1).getPrice() < 0) {
      System.out.println("Add money first!");
    } else {
      money -= bottleArray.get(choice - 1).getPrice();

      System.out.println(
        "KACHUNK! " +
        bottleArray.get(choice - 1).getName() +
        " came out of the dispenser!"
      );
      bottleArray.remove(choice - 1);
    }
  }

  public void returnMoney() {

    System.out.printf("Klink klink. Money came out! You got %.2f€ back\n", money);
    money = 0f;
  }

  public void listBottles() {
    Integer number;
    Bottle bottle;
    for (Integer index = 0; index < bottleArray.size(); index++) {
      number = index + 1;
      bottle = bottleArray.get(index);
      System.out.println(
        number.toString() +
        ". Name: " +
        bottle.getName() +
        "\n	Size: " +
        bottle.getSize() +
        "	Price: " +
        bottle.getPrice()
      );
    }
  }
}
