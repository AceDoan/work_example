package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Work;
import com.example.demo.repository.WorkRepository;
import com.example.demo.service.IWorkService;

@Service
public class WorkServiceImpl implements IWorkService {

    @Autowired
    WorkRepository workRepository;

    @Override
    public Page<Work> getAllWorks(Pageable pageable) {
        return workRepository.findAll(pageable);
    }

    @Override
    public Optional<Work> getWorkById(Long id) {
        return workRepository.findById(id);
    }

    @Override
    public Work save(Work work) {
        return workRepository.save(work);
    }

    @Override
    public void delete(Work work) {
        workRepository.delete(work);
    }
}
