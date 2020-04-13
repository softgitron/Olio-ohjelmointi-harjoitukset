package com.example.limsamaatti;

public class Bottle {
  private String name;
  private String manufacturer;
  private double totalEnergy;
  private double size;
  private double price;

  public Bottle() {
    name = "Pepsi Max";
    manufacturer = "Pepsi";
    totalEnergy = 0.3;
    size = 0.5;
    price = 1.80;
  }

  public Bottle(
    String initialName,
    String initialManufacturer,
    double initialTotalEnergy,
    double initialSize,
    double initialPrice
  ) {
    name = initialName;
    manufacturer = initialManufacturer;
    totalEnergy = initialTotalEnergy;
    size = initialSize;
    price = initialPrice;
  }

  public String getName() {
    return name;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public double getEnergy() {
    return totalEnergy;
  }

  public double getSize() {
    return size;
  }

  public double getPrice() {
    return price;
  }
}
