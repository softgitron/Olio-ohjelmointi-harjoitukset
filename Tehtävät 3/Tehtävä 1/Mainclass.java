class Mainclass {
    public static void main(String args[]) {
        BottleDispenser dispenser = new BottleDispenser(5, 0);
        dispenser.addMoney();
        dispenser.buyBottle();
        dispenser.buyBottle();
        dispenser.addMoney();
        dispenser.addMoney();
        dispenser.buyBottle();
        dispenser.returnMoney();
    }
}