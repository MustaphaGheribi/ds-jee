package com.tekup.ds.repository;

import com.tekup.ds.domain.Met;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetRepository extends JpaRepository<Met, String> {
}
