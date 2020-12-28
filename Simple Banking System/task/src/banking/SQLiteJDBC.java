package banking;

import org.sqlite.SQLiteDataSource;

public class SQLiteJDBC {
    private static String url;
    private static SQLiteDataSource dataSource;
    private static long id = 1;

    public SQLiteJDBC(String fileName) {
        url = "jdbc:sqlite:" + fileName;

        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
    }

    public static SQLiteDataSource getDataSource() {
        return dataSource;
    }
}
