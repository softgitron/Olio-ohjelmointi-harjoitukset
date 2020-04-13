import java.util.Scanner;

class Mainclass {

  private enum MenuType {
    mainMenu,
    characterMenu,
    weaponMenu
  }

  private static Scanner input = new Scanner(System.in);

  public static void main(String args[]) {
    String choice;
    Character character = null;
    do {
      printMenu(MenuType.mainMenu);
      choice = input.nextLine();
      if (choice.startsWith("1")) {
        character = CharacterCreation();
      } else if (choice.startsWith("2")) {
        if (character != null) {
          character.fight();
        } else {
          System.out.println("Create character first!");
        }
      }
    } while (!choice.startsWith("0"));
  }

  private static void printMenu(MenuType type) {
    switch (type) {
      case mainMenu:
        System.out.print(
          "*** BATTLE SIMULATOR ***\n" +
          "1) Create a character\n" +
          "2) Fight with a character\n" +
          "0) Quit\n"
        );
        break;
      case characterMenu:
        System.out.print(
          "Choose your character: \n" +
          "1) King\n" +
          "2) Knight\n" +
          "3) Queen\n" +
          "4) Troll\n"
        );
        break;
      case weaponMenu:
        System.out.print(
          "Choose your weapon: \n" +
          "1) Knife\n" +
          "2) Axe\n" +
          "3) Sword\n" +
          "4) Club\n"
        );
        break;
    }
    System.out.print("Your choice: ");
  }

  private static Character CharacterCreation() {
    String choice;
    printMenu(MenuType.characterMenu);
    choice = input.nextLine();
    Character character;
    switch (choice) {
      case "1":
        character = new King();
        break;
      case "2":
        character = new Knight();
        break;
      case "3":
        character = new Queen();
        break;
      case "4":
        character = new Troll();
        break;
      default:
        return null;
    }
    printMenu(MenuType.weaponMenu);
    choice = input.nextLine();
    Weapon weapon;
    switch (choice) {
      case "1":
        weapon = new Knife();
        break;
      case "2":
        weapon = new Axe();
        break;
      case "3":
        weapon = new Sword();
        break;
      case "4":
        weapon = new Club();
        break;
      default:
        return null;
    }
    character.weapon = weapon;
    return character;
  }
}
