package net.comorevi.nukkitplugin;

import java.sql.*;

public class SQLite3DataProvider {

    private EconomyUIShop plugin;
    private Connection connection = null;

    SQLite3DataProvider(EconomyUIShop plugin) {
        this.plugin = plugin;
        connectSQL();
    }

    private void connectSQL() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().toString() + "/DataDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS UIShop (" +
                            " shop INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " itemID INTEGER NOT NULL," +
                            " damage INTEGER NOT NULL," +
                            " price INTEGER NOT NULL)"
            );
            statement.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    boolean existsShopData(int itemId, int damage) {
        try {
            String sql = "SELECT itemID FROM UIShop WHERE itemID = ? AND damage = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setQueryTimeout(30);
            statement.setInt(1, itemId);
            statement.setInt(2, damage);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                rs.close();
                statement.close();
                return true;
            } else {
                rs.close();
                statement.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    void addShopData(int itemId, int damage, int price) {
        try {
            if (existsShopData(itemId, damage)) return;

            String sql = "INSERT INTO UIShop (itemID, damage, price) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);
            statement.setInt(2, damage);
            statement.setInt(3, price);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void removeShopData(int itemId, int damage) {
        try {
            if (!existsShopData(itemId, damage)) return;

            String sql = "DELETE FROM UIShop WHERE itemID = ? AND damage = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);
            statement.setInt(2, damage);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int getItemPrice(int itemId, int damage) {
        try {
            String sql = "SELECT price FROM UIShop WHERE itemId = ? AND damage = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);
            statement.setInt(2, damage);
            int result = statement.executeQuery(sql).getInt("price");
            statement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 99999999;
    }
}
