package com.job.management.modules.candidate.controllers;


import com.job.management.modules.candidate.entities.Candidate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @PostMapping
    public void createCandidate(@RequestBody Candidate candidate) {
        System.out.println("Candidato:");
        System.out.println(candidate.getFullName());
    }
}
