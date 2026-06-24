/**
 * 보통예금 계좌 — Account 상속
 * - 이율(interestRate) 필드 추가
 * - getAccountType() 오버라이딩
 * - applyInterest(): 이자 적용 기능
 */
public class SavingsAccount extends Account {

    private double interestRate; // 연 이율(%)

    public SavingsAccount(String accountNumber, String ownerName,
                          double initialBalance, double interestRate) {
        super(accountNumber, ownerName, initialBalance);
        this.interestRate = interestRate;
    }

    @Override
    public String getAccountType() { return "보통예금"; }

    public double getInterestRate() { return interestRate; }

    /** 이자 적용 (보통예금 전용 기능) */
    public void applyInterest() {
        double interest = balance * interestRate / 100.0;
        balance += interest;
        System.out.printf("  [보통예금] 이자 %.2f%% 적용 | 이자: %,.0f원 | 잔액: %,.0f원%n",
                interestRate, interest, balance);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | 이율: %.2f%%", interestRate);
    }
}
