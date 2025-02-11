package com.sg.nus.iss.Workshop_21.RESTController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sg.nus.iss.Workshop_21.model.Customer;
import com.sg.nus.iss.Workshop_21.model.Order;
import com.sg.nus.iss.Workshop_21.repo.CustomerRepo;

@RestController
@RequestMapping("/api")
public class CustomerRESTController {

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomerList(@RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        List<Customer> customerList = customerRepo.getAllCustomers(limit, offset);

        if (customerList == null || customerList.isEmpty()) { // Check for null OR empty list
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<String> getCustomerById(@PathVariable Integer customer_id) {
        Customer customer = customerRepo.getCustomerById(customer_id);

        if (customer == null) {
            // Return 404 Not Found if the customer does not exist
            return ResponseEntity.status(404).body("error:Customer not found" + customer_id);
        }
        // Return 200 OK with the customer data
        return ResponseEntity.ok(customer.toString());
    }

    @GetMapping("/customer/{customer_id}/orders")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Integer customer_id) {

        if (!customerRepo.customerExists(customer_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Order> orders = customerRepo.getOrdersByCustomerId(customer_id);

        return ResponseEntity.ok(orders);
    }
}
