package com.ecommerce;

public class OrderService {
    private ProductDAO dao = new ProductDAO();

    public String processOrder(int userId, int productId, int qty) {
        try {
            int stock = dao.getStock(productId);
            if (stock == -1) return "Order Failed: Product record does not exist.";
            if (stock < qty) return "Order Failed: Insufficient inventory available.";

            int balance = dao.getUserBalance(userId);
            if (balance == -1) return "Order Failed: User account record does not exist.";

            int price = dao.getPrice(productId);
            int totalCost = price * qty;
            if (balance < totalCost) return "Order Failed: Insufficient account balance.";

            // If checks clear, execute updates
            dao.executeTransaction(userId, productId, qty, totalCost);
            return "Order Successful! Database states adjusted securely.";

        } catch (Exception e) {
            return "System Exception Occurred: " + e.getMessage();
        }
    }
}
