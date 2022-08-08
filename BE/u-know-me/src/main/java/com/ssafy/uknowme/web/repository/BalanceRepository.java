package com.ssafy.uknowme.web.repository;

import com.ssafy.uknowme.web.domain.Balance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    Optional<Balance> findBySeq(int seq);

    @Query("SELECT b FROM Balance b ORDER BY b.seq DESC")
    List<Balance> findAllDesc();

}
