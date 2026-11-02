package com.sumanth.order_tracking;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public record StatusUpdate(Order.Status status) {}

    private final OrderRepository repo;

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@Valid @RequestBody Order order) {
        return repo.save(order);
    }

    @GetMapping
    public List<Order> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> notFound(id));
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestBody StatusUpdate update) {
        Order order = repo.findById(id).orElseThrow(() -> notFound(id));
        order.setStatus(update.status());
        return repo.save(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw notFound(id);
        }
        repo.deleteById(id);
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order " + id + " not found");
    }
}