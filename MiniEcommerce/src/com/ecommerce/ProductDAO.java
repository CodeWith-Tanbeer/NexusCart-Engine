package com.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO {
    
    public int getStock(int productId) throws Exception {
        String query = "SELECT stock FROM products WHERE id = ?";
        try (Connection con = DBUtility.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, productId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return rs.getInt("stock");
            }
        }
        return -1;
    }

    public int getPrice(int productId) throws Exception {
        String query = "SELECT price FROM products WHERE id = ?";
        try (Connection con = DBUtility.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, productId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return rs.getInt("price");
            }
        }
        return 0;
    }

    public int getUserBalance(int userId) throws Exception {
        String query = "SELECT balance FROM users WHERE id = ?";
        try (Connection con = DBUtility.getConnection();
             PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return rs.getInt("balance");
            }
        }
        return -1;
    }

    public void executeTransaction(int userId, int productId, int qty, int totalCost) throws Exception {
        String updateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";
        String updateBalance = "UPDATE users SET balance = balance - ? WHERE id = ?";
        
        try (Connection con = DBUtility.getConnection()) {
            con.setAutoCommit(false); 
            
            try (PreparedStatement ps1 = con.prepareStatement(updateStock);
                 PreparedStatement ps2 = con.prepareStatement(updateBalance)) {
                
                ps1.setInt(1, qty);
                ps1.setInt(2, productId);
                ps1.executeUpdate();
                
                ps2.setInt(1, totalCost);
                ps2.setInt(2, userId);
                ps2.executeUpdate();
                
                con.commit(); 
            } catch (Exception e) {
                con.rollback(); 
                throw e;
            }
        }
    }
}
