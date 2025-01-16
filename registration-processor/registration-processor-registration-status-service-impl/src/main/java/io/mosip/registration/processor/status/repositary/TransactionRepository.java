package io.mosip.registration.processor.status.repositary;

import java.util.List;

import io.mosip.registration.processor.status.entity.BaseTransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.mosip.kernel.core.dataaccess.spi.repository.BaseRepository;
import io.mosip.registration.processor.status.entity.BaseSyncRegistrationEntity;


@Repository
public interface TransactionRepository<T extends BaseTransactionEntity, E> extends BaseRepository<T, E> {

	@Query("SELECT trn FROM TransactionEntity trn WHERE trn.registrationId=:regId")
	public List<T> getTransactionByRegId(@Param("regId") String regId);

	@Query("SELECT trn FROM TransactionEntity trn WHERE trn.registrationId=:regId and trn.statusCode=:statusCode")
	public List<T> getTransactionByRegIdAndStatusCode(@Param("regId") String regId,
			@Param("statusCode") String statusCode);

	@Query(value = "SELECT EXISTS(SELECT 1 FROM registration_transaction trn WHERE trn.reg_id=:regId and trn.status_code in :statusCodes and trn.trn_type_code=:trnTypeCode and trn.transaction_flow_id = :latestTrnFlowId)", nativeQuery = true)
	boolean existsByRegIdAndTrnTypeCodeAndStatusCode(@Param("regId") String regId, @Param("trnTypeCode") String trnTypeCode, @Param("statusCodes") List<String> statusCode, @Param("latestTrnFlowId")  String latestTrnFlowId);
}
