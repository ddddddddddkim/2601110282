/**
 * 신용계좌 — Account 상속
 * - creditLimit(신용한도) 필드 추가
 * - withdraw() 오버라이딩: 잔액이 0 이하여도 한도 내 출금 가능
 */
public class CreditAccount extends Account {

    private double creditLimit; // 신용한도 (마이너스 허용 범위)

    public CreditAccount(String accountNumber, String ownerName,
                         double initialBalance, double creditLimit) {
        super(accountNumber, ownerName, initialBalance);
        this.creditLimit = creditLimit;
    }

    @Override
    public String getAccountType() { return "신용계좌"; }

    /**
     * 출금 오버라이딩 — 잔액 + 신용한도 범위 내 출금 허용
     */
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        if (balance - amount < -creditLimit)
            throw new IllegalStateException("신용한도를 초과합니다. (한도: " + (long) creditLimit + "원)");
        balance -= amount;
        System.out.printf("  [신용계좌] %,.0f원 출금 완료 | 잔액: %,.0f원 (신용한도: %,.0f원)%n",
                amount, balance, creditLimit);
    }

    public double getCreditLimit() { return creditLimit; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | 신용한도: %,.0f원", creditLimit);
    }
}
