class Dog {
    private String name;

	public Dog(String newName) {
        name = newName;
        System.out.println("Hey, my name is " + name + "!");
    }

    public void speak(String bark) {
        System.out.println(bark);
    }
}