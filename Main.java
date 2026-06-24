import java.util.Scanner;

/**
 * 메인 실행 클래스 — 콘솔 UI 제공
 * - Bank, Account 객체를 생성·조작
 * - 다형성: Account 타입으로 입금/출금 처리
 */
public class Main {

    private static Bank bank = new Bank("Java Bank");
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        while (true) {
            printMenu();
            int choice = readInt("▶ 선택: ");
            System.out.println();
            switch (choice) {
                case 1: menuCreate();       break;
                case 2: menuDeposit();      break;
                case 3: menuWithdraw();     break;
                case 4: menuView();         break;
                case 5: bank.listAllAccounts(); break;
                case 6: menuDelete();       break;
                case 0:
                    System.out.println("  프로그램을 종료합니다. 감사합니다!");
                    sc.close();
                    return;
                default:
                    System.out.println("  잘못된 선택입니다.");
            }
        }
    }

    // ── 메뉴 출력 ────────────────────────────────────────────────

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║      Java Bank 계좌 관리 시스템      ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private static void printMenu() {
        System.out.println("\n┌────────────── 메인 메뉴 ──────────────┐");
        System.out.println("│  1. 계좌 개설      2. 입금             │");
        System.out.println("│  3. 출금           4. 계좌 조회        │");
        System.out.println("│  5. 전체 계좌 목록 6. 계좌 해지        │");
        System.out.println("│  0. 종료                               │");
        System.out.println("└───────────────────────────────────────┘");
    }

    // ── 계좌 개설 ────────────────────────────────────────────────

    private static void menuCreate() {
        System.out.println("  [계좌 개설]");
        System.out.println("  1. 보통예금  2. 정기적금  3. 신용계좌");
        int type = readInt("  종류: ");
        System.out.print("  예금주 이름: ");
        String name = sc.nextLine().trim();
        double init = readDouble("  초기 입금액: ");

        try {
            switch (type) {
                case 1:
                    double rate = readDouble("  연 이율(%): ");
                    bank.createSavingsAccount(name, init, rate);
                    break;
                case 2:
                    int months = readInt("  기간(개월): ");
                    bank.createFixedDepositAccount(name, init, months);
                    break;
                case 3:
                    double limit = readDouble("  신용한도(원): ");
                    bank.createCreditAccount(name, init, limit);
                    break;
                default:
                    System.out.println("  잘못된 종류입니다.");
            }
        } catch (Exception e) {
            System.out.println("  오류: " + e.getMessage());
        }
    }

    // ── 입금 ────────────────────────────────────────────────────

    private static void menuDeposit() {
        Account acc = findOrPrint();
        if (acc == null) return;
        double amount = readDouble("  입금액: ");
        try {
            acc.deposit(amount);           // 다형성: Account 참조로 호출
        } catch (Exception e) {
            System.out.println("  오류: " + e.getMessage());
        }
    }

    // ── 출금 ────────────────────────────────────────────────────

    private static void menuWithdraw() {
        Account acc = findOrPrint();
        if (acc == null) return;
        double amount = readDouble("  출금액: ");
        try {
            acc.withdraw(amount);          // 다형성: 하위 클래스 오버라이딩 메서드 호출
        } catch (Exception e) {
            System.out.println("  오류: " + e.getMessage());
        }
    }

    // ── 계좌 조회 ────────────────────────────────────────────────

    private static void menuView() {
        Account acc = findOrPrint();
        if (acc == null) return;
        System.out.println("  " + acc);

        // instanceof로 타입 확인 → 보통예금일 때 이자 적용 제안
        if (acc instanceof SavingsAccount) {
            System.out.print("  이자를 적용하시겠습니까? (y/n): ");
            if (sc.nextLine().trim().equalsIgnoreCase("y")) {
                ((SavingsAccount) acc).applyInterest();
            }
        }
    }

    // ── 계좌 해지 ────────────────────────────────────────────────

    private static void menuDelete() {
        System.out.print("  해지할 계좌번호: ");
        String no = sc.nextLine().trim();
        if (bank.deleteAccount(no)) {
            System.out.println("  계좌가 해지되었습니다.");
        } else {
            System.out.println("  계좌를 찾을 수 없습니다.");
        }
    }

    // ── 공통 유틸 ────────────────────────────────────────────────

    private static Account findOrPrint() {
        System.out.print("  계좌번호: ");
        String no = sc.nextLine().trim();
        Account acc = bank.findAccount(no);
        if (acc == null) System.out.println("  계좌를 찾을 수 없습니다.");
        return acc;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  숫자를 입력해주세요.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = sc.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("  숫자를 입력해주세요.");
            }
        }
    }
}
