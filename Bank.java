import java.util.ArrayList;
import java.util.List;

/**
 * 은행 클래스 — 계좌 목록 관리 및 CRUD 담당
 * - 다형성: List<Account>에 다양한 계좌 타입 저장
 * - 캡슐화: accounts 리스트를 private으로 보호
 */
public class Bank {

    private String bankName;
    private List<Account> accounts; // 다형성: Account 참조로 다양한 계좌 관리
    private int nextId;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new ArrayList<>();
        this.nextId = 1000;
    }

    private String generateAccountNumber() {
        return String.format("BANK-%04d", nextId++);
    }

    // ── 계좌 생성 ─────────────────────────────────────────────

    public SavingsAccount createSavingsAccount(String ownerName,
                                               double initialBalance,
                                               double interestRate) {
        SavingsAccount acc = new SavingsAccount(
                generateAccountNumber(), ownerName, initialBalance, interestRate);
        accounts.add(acc);
        System.out.println("  ✔ 보통예금 계좌 개설 완료: " + acc.getAccountNumber());
        return acc;
    }

    public FixedDepositAccount createFixedDepositAccount(String ownerName,
                                                         double initialBalance,
                                                         int termMonths) {
        FixedDepositAccount acc = new FixedDepositAccount(
                generateAccountNumber(), ownerName, initialBalance, termMonths);
        accounts.add(acc);
        System.out.println("  ✔ 정기적금 계좌 개설 완료: " + acc.getAccountNumber());
        return acc;
    }

    public CreditAccount createCreditAccount(String ownerName,
                                             double initialBalance,
                                             double creditLimit) {
        CreditAccount acc = new CreditAccount(
                generateAccountNumber(), ownerName, initialBalance, creditLimit);
        accounts.add(acc);
        System.out.println("  ✔ 신용계좌 개설 완료: " + acc.getAccountNumber());
        return acc;
    }

    // ── 조회 ────────────────────────────────────────────────────

    public Account findAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) return acc;
        }
        return null;
    }

    public void listAllAccounts() {
        System.out.println("\n══════════ " + bankName + " 전체 계좌 목록 ══════════");
        if (accounts.isEmpty()) {
            System.out.println("  등록된 계좌가 없습니다.");
        } else {
            for (Account acc : accounts) {
                System.out.println("  " + acc);
            }
            System.out.println("  총 " + accounts.size() + "개 계좌");
        }
        System.out.println("═══════════════════════════════════════════");
    }

    // ── 삭제 ────────────────────────────────────────────────────

    public boolean deleteAccount(String accountNumber) {
        Account acc = findAccount(accountNumber);
        if (acc != null) {
            accounts.remove(acc);
            return true;
        }
        return false;
    }

    public String getBankName() { return bankName; }
    public int getAccountCount() { return accounts.size(); }
}
