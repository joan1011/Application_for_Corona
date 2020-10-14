package com.anand.Application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CoronaRepository extends JpaRepository<Corona,Long> {
    List<Corona> findByLastUpdateBetween(LocalDateTime from, LocalDateTime to);

    List<Corona> findByCombinedKey(String combinedKey);
}
