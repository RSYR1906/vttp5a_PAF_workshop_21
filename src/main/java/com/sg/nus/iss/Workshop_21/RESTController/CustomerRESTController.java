package com.sg.nus.iss.Workshop_21.RESTController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.nus.iss.Workshop_21.model.Customer;
import com.sg.nus.iss.Workshop_21.model.Order;
import com.sg.nus.iss.Workshop_21.repo.CustomerDAO;

@RestController
@RequestMapping("/api")
public class CustomerRESTController {

    @Autowired
    private CustomerDAO customerDAO;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomerList(int limit, int offset) {

        List<Customer> customerList = customerDAO.getAllCustomers(limit, offset);

        return ResponseEntity.ok().body(customerList);
    }

    @GetMapping("/customers/{customer_id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer customer_id) {
        Customer customer = customerDAO.getCustomerById(customer_id);

        if (customer == null) {
            // Return 404 Not Found if the customer does not exist
            return ResponseEntity.status(404).body(Map.of("error", "Customer not found", "customer_id", customer_id));
        }

        // Return 200 OK with the customer data
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customers/{customer_id}/orders")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Integer customer_id) {

        if (!customerDAO.customerExists(customer_id)) {
            return ResponseEntity.status(404).body(Map.of("error", "Customer not found", "customer_id", customer_id));
        }

        List<Order> orders = customerDAO.getOrdersByCustomerId(customer_id);

        return ResponseEntity.ok(orders);
    }
}
