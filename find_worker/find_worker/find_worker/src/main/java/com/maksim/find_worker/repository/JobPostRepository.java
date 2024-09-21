package com.maksim.find_worker.repository;


import com.maksim.find_worker.domain.JobPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    Optional<JobPost> findById(Long id);
    Page<JobPost> findAll(Pageable pageable);
    List<JobPost> findByClientId(Long clientId);
}
