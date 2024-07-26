package com.job.management.modules.company.dto;

public record CreateJobDTO(
  String title,
  String description,
  String benefits,
  String level,
  String requirements,
  String salary,
  String location,
  String status
) {
}
