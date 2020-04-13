package com.example.limsamaatti;

import java.util.ArrayList;

public class BottleDispenser {
  // The array for the Bottle-objects
  private ArrayList<Bottle> bottleArray = new ArrayList<Bottle>();
  private float money = 10;
  private Bottle lastBottle = null;
  private static BottleDispenser dispenser = new BottleDispenser();

  private BottleDispenser(){};

  public static BottleDispenser getInstance() {
    return dispenser;
  }

  public void addBottle(Bottle bottle) {
    bottleArray.add(bottle);
  }

  public void addMoney(float moreMoney) {
    money += moreMoney;

    System.out.println("Klink! Added more money!");
  }

  public String buyBottle(String choice) {
    Bottle bottle = getBottleByName(choice);
    if (bottle == null) {
      System.out.println("Outs! No such bottle.");
      return "Sorry, that bottle is not available.";
    } else if (money - bottle.getPrice() < 0) {
      System.out.println("Please, add money first!");
      return "You don't have enough money.";
    } else {
      lastBottle = bottle;
      money -= bottle.getPrice();
      bottleArray.remove(bottle);
      return (
        "KACHUNK! " + bottle.getName() +
        " came out of the dispenser!"
      );
    }
  }

  private Bottle getBottleByName(String details) {
    String[] parameters = details.split(",");
    String name = parameters[0];
    double size = Double.parseDouble(parameters[1].substring(0,parameters[1].length() - 1));
    double price = Double.parseDouble(parameters[2].substring(0,parameters[2].length() - 1));

    for (Bottle bottle: bottleArray) {
      if (bottle.getName().contains(name) && bottle.getSize() == size && bottle.getPrice() == price) {
        return bottle;
      }
    }
    return null;
  }

  public float getBalance() {
    return money;
  }

  public String returnMoney() {
    String message = String.format("Klink klink. Money came out! You got %.2f€ back\n", money);
    money = 0f;
    return message;
  }

  public String listBottles() {
    Integer number;
    Bottle bottle;
    String list = "";
    for (Integer index = 0; index < bottleArray.size(); index++) {
      number = index + 1;
      bottle = bottleArray.get(index);
      list +=
        number.toString() +
        ". Name: " +
        bottle.getName() +
        "\n	Size: " +
        bottle.getSize() +
        "	Price: " +
        bottle.getPrice() +
                "\n"
      ;
    }
    return list;
  }

  public String getReceipt() {
    String output;
    if (lastBottle != null) {
      output = String.format("Receipts:\nName: %s\nPrice: %.2f€\nSize: %.2fL\n", lastBottle.getName(), lastBottle.getPrice(), lastBottle.getSize());
    } else {
      output = "No receipt.\n";
    }
    return output;
  }
}
