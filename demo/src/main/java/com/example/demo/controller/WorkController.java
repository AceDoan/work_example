package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Work;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.impl.WorkServiceImpl;

@RestController
@RequestMapping(value = "/api/v1")
public class WorkController {
    @Autowired
    private WorkServiceImpl workServiceImpl;

    @GetMapping("/works")
    public Page<Work> getAllWorks(@PageableDefault(page = 0, size = 10) @SortDefault.SortDefaults({
            @SortDefault(sort = "name", direction = Sort.Direction.DESC),
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) }) Pageable pageable) {
        return workServiceImpl.getAllWorks(pageable);
    }

    @GetMapping("/works/{id}")
    public ResponseEntity<Work> getWorkById(@PathVariable(value = "id") Long workId) throws ResourceNotFoundException {
        Work work = workServiceImpl.getWorkById(workId)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found for this id :: " + workId));
        return ResponseEntity.ok().body(work);
    }

    @PostMapping("/works")
    public Work createWork(@Valid @RequestBody Work work) {
        return workServiceImpl.save(work);
    }

    @PutMapping("/works/{id}")
    public ResponseEntity<Work> updateWork(@PathVariable(value = "id") Long workId, @Valid @RequestBody Work request)
            throws ResourceNotFoundException {
        Work work = workServiceImpl.getWorkById(workId)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found for this id :: " + workId));
        work.setName(request.getName());
        work.setStartingDate(request.getStartingDate());
        work.setEndingDate(request.getEndingDate());
        work.setStatus(request.getStatus());

        final Work updatedEmployee = workServiceImpl.save(work);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/works/{id}")
    public Map<String, Boolean> deleteWork(@PathVariable(value = "id") Long workId) throws ResourceNotFoundException {
        Work work = workServiceImpl.getWorkById(workId)
                .orElseThrow(() -> new ResourceNotFoundException("Work not found for this id :: " + workId));

        workServiceImpl.delete(work);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
