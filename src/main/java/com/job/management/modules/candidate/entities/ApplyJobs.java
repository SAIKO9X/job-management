package com.job.management.modules.candidate.entities;

import com.job.management.modules.company.entities.Jobs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "apply_jobs")
public class ApplyJobs {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
  private Candidate candidate;

  @ManyToOne
  @JoinColumn(name = "job_id", insertable = false, updatable = false)
  private Jobs jobs;

  @Column(name = "candidate_id")
  private UUID candidateId;

  @Column(name = "job_id")
  private UUID jobId;

  @CreationTimestamp
  private LocalDateTime createdAt;
}