class Dog {
  private String name;

  public Dog(String newName) {
    if (newName.trim().isEmpty()) {
      name = "Doge";
    } else {
      name = newName;
    }
    System.out.println("Hey, my name is " + name + "!");
  }

  public boolean speak(String bark) {
    boolean error = false;
    if (bark.trim().isEmpty()) {
        bark = "Much wow!";
        error = true;
    }
    System.out.println(name + ": " + bark);
    return error;
  }
}
