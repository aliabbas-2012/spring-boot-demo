package com.dev.demo.tutorial.controller;

import java.util.List;
import com.dev.demo.auth.access.AuthorizeOwnership;
import com.dev.demo.base.BaseController;
import com.dev.demo.response.PaginationResponseEntity;
import com.dev.demo.tutorial.mapper.TutorialMapper;
import com.dev.demo.tutorial.model.Tutorial;
import com.dev.demo.tutorial.service.TutorialService;
import com.dev.demo.tutorial.validation.request.CreateUpdateRequest;
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


@RestController
@RequestMapping("/api/tutorials")
public class TutorialController extends BaseController {

    private final TutorialService service;

    @Autowired
    public TutorialController(TutorialService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllEntities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(defaultValue = "") String search
    ) {
        List<Order> orders = getSortOrders(sort);
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        return new PaginationResponseEntity<>(service.getAllEntities(pagingSort, search));
    }

    @GetMapping("/{id}")
    public Tutorial getEntityById(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping
    public ResponseEntity<?> createEntity(@Valid @RequestBody CreateUpdateRequest createUpdateRequest) {
        Tutorial tutorial = TutorialMapper.INSTANCE.toTutorial(createUpdateRequest);
        service.createEntity(tutorial);

        return new ResponseEntity<>("Tutorial created Successfully!", HttpStatus.CREATED);
    }

    @AuthorizeOwnership(value={"ROLE_ADMIN","ROLE_MOD"})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable Long id,
        @Valid @RequestBody CreateUpdateRequest createUpdateRequest) {

        Tutorial user = TutorialMapper.INSTANCE.toTutorial(createUpdateRequest);
        service.updateEntity(id, user);
        return new ResponseEntity<>("Tutorial updated Successfully!", HttpStatus.OK);
    }

    @AuthorizeOwnership(value={"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        service.deleteEntity(id);
    }
}
