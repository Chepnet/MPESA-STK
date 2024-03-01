package com.C2B.MpesaSTK.Repository;


import com.C2B.MpesaSTK.Model.LogStkRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogStkRequestRepository extends JpaRepository<LogStkRequest,Integer> {
    Optional<LogStkRequest> findByTransactionRef (String transactionRef);
}
