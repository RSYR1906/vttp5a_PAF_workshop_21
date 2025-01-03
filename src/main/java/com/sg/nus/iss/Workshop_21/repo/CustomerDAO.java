package com.sg.nus.iss.Workshop_21.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.sg.nus.iss.Workshop_21.model.Customer;
import com.sg.nus.iss.Workshop_21.model.Order;

@Repository
public class CustomerDAO {

    @Autowired
    private JdbcTemplate template;

    public List<Customer> getAllCustomers(final int limit, final int offset) {
        final List<Customer> customerList = new ArrayList<>();

        final SqlRowSet rs = template.queryForRowSet("SELECT * FROM customers LIMIT ? OFFSET ?", limit, offset);

        while (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt("CustomerID"));
            customer.setName(rs.getString("CustomerName"));
            customer.setContactName(rs.getString("ContactName"));
            customer.setAddress(rs.getString("Address"));
            customer.setCity(rs.getString("City"));
            customer.setPostalCode(rs.getString("PostalCode"));
            customer.setCountry(rs.getString("Country"));

            customerList.add(customer);
        }

        return Collections.unmodifiableList(customerList);
    }

    public Customer getCustomerById(final Integer id) {
        final SqlRowSet rs = template.queryForRowSet("SELECT * FROM customers WHERE CustomerID = ?", id);

        if (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt("CustomerID"));
            customer.setName(rs.getString("CustomerName"));
            customer.setContactName(rs.getString("ContactName"));
            customer.setAddress(rs.getString("Address"));
            customer.setCity(rs.getString("City"));
            customer.setPostalCode(rs.getString("PostalCode"));
            customer.setCountry(rs.getString("Country"));
            return customer;
        } else {
            // Handle the case where no rows are returned
            return null;
        }
    }

    public boolean customerExists(Integer customerId) {
        String sql = "SELECT COUNT(*) FROM customers WHERE CustomerID = ?";
        Integer count = template.queryForObject(sql, Integer.class, customerId);
        return count != null && count > 0;
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        String sql = "SELECT * FROM orders WHERE CustomerID = ?";
        SqlRowSet rs = template.queryForRowSet(sql, customerId);

        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order();
            order.setCustomerId(rs.getInt("CustomerID"));
            order.setOrderId(rs.getInt("OrderID"));

            orders.add(order);
        }

        return orders;
    }
}
