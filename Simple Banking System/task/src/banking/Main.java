package banking;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0 && args[0].equals("-fileName")) {
            SQLiteJDBC jdbc = new SQLiteJDBC(args[1]);
            UI.show();
        }
    }
}
