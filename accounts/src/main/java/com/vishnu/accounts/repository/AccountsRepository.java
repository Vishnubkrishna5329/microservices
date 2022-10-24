package com.vishnu.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vishnu.accounts.model.Accounts;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

	Accounts findByCustomerId(int customerId);

}
