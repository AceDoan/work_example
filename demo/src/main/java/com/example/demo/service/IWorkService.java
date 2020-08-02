package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Work;

public interface IWorkService {
    
    public Page<Work> getAllWorks(Pageable pageable);

    public Optional<Work> getWorkById(Long id);
    
    public Work save(Work work);
    
    public void delete(Work work);
}
