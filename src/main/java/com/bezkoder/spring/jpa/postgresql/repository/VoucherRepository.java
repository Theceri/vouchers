package com.bezkoder.spring.jpa.postgresql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bezkoder.spring.jpa.postgresql.model.Voucher;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	List<Voucher> findByPublishedAndUsername(boolean published,String username);

	List<Voucher> findByTitleContaining(String title);


}
