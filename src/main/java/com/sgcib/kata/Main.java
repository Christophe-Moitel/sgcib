package com.sgcib.kata;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Stack;

import com.sgcib.kata.bean.OperationBean;
import com.sgcib.kata.exception.AccountFunctionalException;
import com.sgcib.kata.exception.AccountTechnicalException;
import com.sgcib.kata.service.AccountDepositService;
import com.sgcib.kata.service.AccountHistoryService;
import com.sgcib.kata.service.AccountWithdrawalService;
import com.sgcib.kata.service.impl.AccountDepositServiceImpl;
import com.sgcib.kata.service.impl.AccountHistoryServiceImpl;
import com.sgcib.kata.service.impl.AccountWithdrawalServiceImpl;

/**
 * <p>
 * Programme principal exécuté en mode interactif :<br />
 * Le client se voit demandé un numéro de compte, et a la possibilité de
 * <ol>
 * <li>Déposer de l'argent</li>
 * <li>Retirer de l'argent</li>
 * <li>Visualiser ses opérations</li>
 * </ol>
 * Avec à chaque interaction une restitution de son solde.
 * </p>
 * 
 * <p>
 * Le programme est un <em>jar</em> exécutable (java -jar ...) prenant en
 * paramètre le(s) numéro(s) de compte(s) à créer initialement.
 * </p>
 * 
 * @author Christophe Moitel
 */
public class Main {

	private static final String WITHDRAWAL_OP = "w";
	private static final String DEPOSIT_OP = "d";
	private static final String VIEW_OP = "v";
	private static final String ACCOUNT_OP = "a";
	private static final String STOP_OP = "s";

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println(usage());
			return;
		}

		executeDesk(args);
	}

	/**
	 * Méthode d'exécution du programme guichet/client.
	 */
	private static void executeDesk(String[] args) {
		Console console = System.console();

		if (console == null) {
			System.out.println("No console associated with current Java virtual machine");
			return;
		}

		AccountHistoryService accountHistoryService = new AccountHistoryServiceImpl(
				Arrays.asList(args).stream().map(Long::parseLong).toArray(s -> new Long[s]));
		AccountDepositService accountDepositService = new AccountDepositServiceImpl(accountHistoryService);
		AccountWithdrawalService accountWithdrawalService = new AccountWithdrawalServiceImpl(accountHistoryService);

		long accountNumber = selectAccount();

		while (true) {
			try {
				String operationType = "";

				while (!operationType.matches(
						String.format("(?i)[%s]", DEPOSIT_OP + WITHDRAWAL_OP + VIEW_OP + ACCOUNT_OP + STOP_OP))) {
					operationType = console.readLine(
							"Do you want to [D]eposit / [W]ithdraw / [V]iew operations | Switch [A]ccount / [S]Top ? ");
				}

				if (operationType.matches(String.format("(?i)%s", DEPOSIT_OP))) {
					BigDecimal amount = new BigDecimal(console.readLine("What amount do you want to deposit ? "));
					accountDepositService.deposit(accountNumber, amount);

					displayBalance(accountHistoryService.getHistory(accountNumber));
					console.printf("%n");
				} else if (operationType.matches(String.format("(?i)%s", WITHDRAWAL_OP))) {
					BigDecimal amount = new BigDecimal(console.readLine("What amount do you want to withdraw ? "));
					accountWithdrawalService.withdraw(accountNumber, amount);

					displayBalance(accountHistoryService.getHistory(accountNumber));
					console.printf("%n");
				} else if (operationType.matches(String.format("(?i)%s", VIEW_OP))) {
					displayOperations(accountHistoryService.getHistory(accountNumber));
					console.printf("%n");
				} else if (operationType.matches(String.format("(?i)%s", ACCOUNT_OP))) {
					accountNumber = selectAccount();
					continue;
				} else if (operationType.matches(String.format("(?i)%s", STOP_OP))) {
					console.printf("Bye%n%n");
					break;
				}
			} catch (RuntimeException | AccountTechnicalException | AccountFunctionalException e) {
				System.err.println(String.format("ERROR | Something went wrong : %s%n", e.getMessage()));
			}
		}
	}

	/**
	 * Affiche toutes les opérations du compte client.
	 */
	private static void displayOperations(Stack<OperationBean> history) {
		for (var operation : history) {
			System.console().printf("OP %1$tY-%1$tm-%1$td %1$tT | %2$10s | %3$.2f | %4$.2f%n", operation.getDate(),
					operation.getOperationType().name(), operation.getAmount(), operation.getBalance());
		}
	}

	/**
	 * Affiche le solde du compte client.
	 */
	private static void displayBalance(Stack<OperationBean> operations) {
		System.console().printf("Your balance is %.2f%n", operations.peek().getBalance());
	}

	/**
	 * Demande le numéro de compte client jusqu'à obtention d'un numéro valide (de
	 * type {@link Long}).
	 */
	private static long selectAccount() {
		while (true) {
			try {
				return Long.parseLong(System.console().readLine("What is your account number ? "));
			} catch (NumberFormatException ife) {
				System.err.println("Not a valid format !");
			}
		}
	}

	/**
	 * Affiche la fonction d'usage.
	 */
	private static String usage() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("java -jar prog.jar Comma separated list of client account numbers"));
		sb.append(String.format("%n%n"));
		sb.append(String.format("Ex. java -jar bank.jar 12345 88888 99999"));

		return sb.toString();
	}
}
