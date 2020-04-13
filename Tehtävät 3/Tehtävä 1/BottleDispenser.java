public class BottleDispenser {
  private int bottles;

  private int money;

  public BottleDispenser(int initialBottles, int initialMoney) {
    bottles = initialBottles;

    money = initialMoney;
  }

  public void addMoney() {
    money += 1;

    System.out.println("Klink! Added more money!");
  }

  public void buyBottle() {
    if (money == 0) {
        System.out.println("Add money first!");
    }
    else if (bottles > 0) {
        bottles -= 1;
        money -= 1;

        System.out.println("KACHUNK! A bottle came out of the dispenser!");
    } else {
        System.out.println("Outs! No bottles left");
    }
  }

  public void returnMoney() {
    money = 0;

    System.out.println("Klink klink. Money came out!");
  }
}
