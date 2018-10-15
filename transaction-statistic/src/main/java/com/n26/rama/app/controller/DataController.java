package com.n26.rama.app.controller;

import static java.time.Instant.now;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.HttpURLConnection;
import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.rama.app.dto.Statistics;
import com.n26.rama.app.dto.Transaction;
import com.n26.rama.app.exception.FutureDateException;
import com.n26.rama.app.exception.OldTransactionException;
import com.n26.rama.app.service.TransactionService;
import com.n26.rama.app.util.DataConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Transaction and statistics operation controller api")
@RestController
@RequestMapping("/")
public class DataController {
	private static final Logger log = LoggerFactory.getLogger(DataController.class);
	private final TransactionService transactionService;

	public DataController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@ApiOperation(value = "Create new transaction")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Success", response = String.class),
			@ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Transaction is older than 60 seconds", response = String.class),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "JSON is invalid", response = String.class),
			@ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Transaction Fields are not parsable or the transaction date is in the future", response = String.class) })
	@RequestMapping(value = "transactions", method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createTransaction(@RequestBody Transaction transaction) {
		if (isOldTransactionDate(transaction.getTimestamp())) {
			throw new OldTransactionException("transaction is older than 60 seconds");
		}
		if (isFutureTransactionDate(transaction.getTimestamp())) {
			throw new FutureDateException("transaction date is future date");
		}

		transactionService.createTransaction(transaction);
	}

	@ApiOperation(value = "Retrieve statistics for 60 seconds", response = Statistics.class)
	@RequestMapping(value = "statistics", method = GET, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Statistics getStatistics() {
		return transactionService.getStatistics();
	}

	@ApiOperation(value = "Delete all existing transaction")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Success", response = String.class) })
	@RequestMapping(value = "transactions", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTransaction() {
		transactionService.deleteTransaction();
	}

	private boolean isOldTransactionDate(Instant time) {
		long transactionSeconds = Duration.between(time, now()).getSeconds();
		log.info("Transaction Seconds value: {}", transactionSeconds);
		return transactionSeconds >= DataConstants.TIME_INTERVAL;
	}

	private boolean isFutureTransactionDate(Instant time) {
		long currentTimeMilliseconds = now().toEpochMilli();
		long transactionTimeMilliseconds = time.toEpochMilli();
		log.info("transactionTimeMilliseconds: {},currentTimeMilliseconds:{}", transactionTimeMilliseconds,
				currentTimeMilliseconds);
		return transactionTimeMilliseconds > currentTimeMilliseconds;
	}
}
