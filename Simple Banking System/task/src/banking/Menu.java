package banking;

public abstract class Menu {
    protected String[] menuItems;

    public void printMenu() {
        for (int i = 0; i < menuItems.length; i++) {
            System.out.println(i + ". " + menuItems[i]);
        }
        System.out.print('>');
    }
}
