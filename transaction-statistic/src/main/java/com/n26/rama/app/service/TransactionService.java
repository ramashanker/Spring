package com.n26.rama.app.service;

import com.n26.rama.app.dto.Statistics;
import com.n26.rama.app.dto.Transaction;

public interface TransactionService {
	void createTransaction(Transaction transaction);

	Statistics getStatistics();

	void deleteTransaction();

}
