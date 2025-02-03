package com.dev.demo.user.controller;

import java.util.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.dev.demo.user.mapper.UserMapper;
import com.dev.demo.user.model.User;
import com.dev.demo.user.service.UserService;
import com.dev.demo.user.validation.request.CreateUpdateRequest;
import com.dev.demo.auth.access.AuthorizeOwnership;
import com.dev.demo.response.PaginationResponseEntity;
import com.dev.demo.base.BaseController;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllEntities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String[] filters,
            @RequestParam(defaultValue = "") String[] searchColumns
    ) {
        List<Order> orders = getSortOrders(sort);
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        System.out.println(Arrays.toString(filters));
        return new PaginationResponseEntity<>(service.getAllEntities(pagingSort, search, filters, searchColumns));
    }

    @GetMapping("/{id}")
    public User getEntityById(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createEntity(@Valid @RequestBody CreateUpdateRequest createUpdateRequest) {
        User user = UserMapper.INSTANCE.toUser(createUpdateRequest);
        Set<String> requestedRoles = createUpdateRequest.getRequestedRoles();
        service.createEntity(user, requestedRoles);

        return new ResponseEntity<>("User created Successfully!", HttpStatus.CREATED);
    }

    @AuthorizeOwnership(value={"ROLE_ADMIN","ROLE_MOD"})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable Long id,
        @Valid @RequestBody CreateUpdateRequest createUpdateRequest) {

        User user = UserMapper.INSTANCE.toUser(createUpdateRequest);
        Set<String> requestedRoles = createUpdateRequest.getRequestedRoles();
        service.updateEntity(id, user, requestedRoles);
        return new ResponseEntity<>("User updated Successfully!", HttpStatus.OK);
    }

    @AuthorizeOwnership(value={"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        service.deleteEntity(id);
    }
}
