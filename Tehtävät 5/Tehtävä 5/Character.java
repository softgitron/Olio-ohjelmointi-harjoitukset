class Character {
  public Weapon weapon;

  public void fight() {
    String className = getClass().getName();
    System.out.print(className + " fights with ");
    weapon.useWeapon();
  }
}

class King extends Character {}

class Knight extends Character {}

class Queen extends Character {}

class Troll extends Character {}

class Weapon {

  public void useWeapon() {
    String weaponName = getClass().getName();
    System.out.print("weapon " + weaponName + "\n");
  }
}

class Knife extends Weapon {}

class Axe extends Weapon {}

class Sword extends Weapon {}

class Club extends Weapon {}
