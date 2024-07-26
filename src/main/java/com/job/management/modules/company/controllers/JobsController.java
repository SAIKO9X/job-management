package com.job.management.modules.company.controllers;

import com.job.management.modules.company.dto.CreateJobDTO;
import com.job.management.modules.company.entities.Jobs;
import com.job.management.modules.company.usecases.CreateJobsUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/jobs")
public class JobsController {

    @Autowired
    private CreateJobsUseCase createJobsUseCase;

    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            var jobs = Jobs.builder()
                    .benefits(createJobDTO.benefits())
                    .companyId(UUID.fromString(companyId.toString()))
                    .description(createJobDTO.description())
                    .level(createJobDTO.level())
                    .build();

            var result = this.createJobsUseCase.execute(jobs);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
