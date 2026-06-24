/**
 * 계좌의 공통 속성과 동작을 정의하는 추상 클래스
 * - 접근제한(private 필드 + public getter)
 * - 생성자
 * - 추상 메서드(다형성 기반)
 */
public abstract class Account {

    private String accountNumber;  // 계좌번호 (외부 직접 접근 불가)
    private String ownerName;      // 예금주
    protected double balance;      // 잔액 (하위 클래스 접근 허용)

    // 생성자
    public Account(String accountNumber, String ownerName, double initialBalance) {
        if (initialBalance < 0) throw new IllegalArgumentException("초기 잔액은 0 이상이어야 합니다.");
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    // 추상 메서드 — 하위 클래스에서 반드시 오버라이딩
    public abstract String getAccountType();

    // 입금 (공통 로직)
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("입금액은 0보다 커야 합니다.");
        balance += amount;
        System.out.printf("  [%s] %.0f원 입금 완료 | 잔액: %,.0f원%n",
                getAccountType(), amount, balance);
    }

    // 출금 (하위 클래스에서 오버라이딩 가능)
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("출금액은 0보다 커야 합니다.");
        if (amount > balance) throw new IllegalStateException("잔액이 부족합니다.");
        balance -= amount;
        System.out.printf("  [%s] %.0f원 출금 완료 | 잔액: %,.0f원%n",
                getAccountType(), amount, balance);
    }

    // Getter
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    public double getBalance()       { return balance; }

    @Override
    public String toString() {
        return String.format("%-14s | 예금주: %-8s | 종류: %-8s | 잔액: %,10.0f원",
                accountNumber, ownerName, getAccountType(), balance);
    }
}
