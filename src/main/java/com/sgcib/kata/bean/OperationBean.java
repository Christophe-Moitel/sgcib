package com.sgcib.kata.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Objet regroupant les informations relatives à une opération cliente.
 * 
 * @author Christophe Moitel
 */
public class OperationBean implements Serializable {

	/**
	 * Identifiant de sérialisation.
	 */
	private static final long serialVersionUID = -1763958514734445151L;

	/**
	 * Type de l'opération.
	 */
	private OperationType operationType;

	/**
	 * Montant de l'opération (signé).
	 */
	private BigDecimal amount;

	/**
	 * Solde du compte après opération.
	 */
	private BigDecimal balance;

	/**
	 * Date de l'opération.
	 */
	private LocalDateTime date;

	public OperationBean(OperationType operationType, BigDecimal amount, BigDecimal balance) {
		this(operationType, amount, balance, LocalDateTime.now());
	}

	public OperationBean(OperationType operationType, BigDecimal amount, BigDecimal balance, LocalDateTime date) {
		this.operationType = operationType;
		this.amount = amount;
		this.balance = balance;
		this.date = date;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("OP %1$tY-%1$tm-%1$td %1$tT | %2$10s | %3$.2f | %4$.2f%n", this.getDate(),
				this.getOperationType().name(), this.getAmount(), this.getBalance());
	}
}
