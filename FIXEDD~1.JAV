/**
 * 정기적금 계좌 — Account 상속
 * - 만기 기간(termMonths) 필드 추가
 * - withdraw() 오버라이딩: 중도해지 수수료(50%) 부과
 */
public class FixedDepositAccount extends Account {

    private int termMonths;                     // 적금 기간(개월)
    private static final double PENALTY_RATE = 0.5; // 중도해지 수수료 비율

    public FixedDepositAccount(String accountNumber, String ownerName,
                               double initialBalance, int termMonths) {
        super(accountNumber, ownerName, initialBalance);
        this.termMonths = termMonths;
    }

    @Override
    public String getAccountType() { return "정기적금"; }

    /**
     * 출금 오버라이딩 — 중도해지 시 수수료 50% 추가 차감
     */
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        double penalty = amount * PENALTY_RATE;
        double totalDeduct = amount + penalty;
        if (totalDeduct > balance) throw new IllegalStateException("잔액이 부족합니다. (수수료 포함)");
        balance -= totalDeduct;
        System.out.printf("  [정기적금] 중도해지 수수료 %,.0f원 발생 | 출금: %,.0f원 | 잔액: %,.0f원%n",
                penalty, amount, balance);
    }

    public int getTermMonths() { return termMonths; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | 기간: %d개월", termMonths);
    }
}
