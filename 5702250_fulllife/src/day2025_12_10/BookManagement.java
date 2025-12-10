package day2025_12_10;

/*
 * =========================================
 *  파일 이름 : BookManagement.java
 *  프로그램 : 컬렉션, 제네릭, 클래스를 이용한
 *            간단한 도서 관리 프로그램
 *
 *  주요 기능 요약
 *    - 도서(Book) 등록 / 삭제 / 전체 조회 / 제목 검색
 *    - 회원(Member) 등록 / 삭제 / 전체 조회
 *    - 대출 / 반납 기능
 *        · 한 회원은 여러 권 대출 가능
 *        · 도서 1권은 동시에 한 명에게만 대출
 *    - 정렬 기능
 *        · 제목 오름차순 정렬 출력
 *        · 출판연도 내림차순 정렬 출력
 *    - 통계 기능
 *        · 현재 대출 중인 도서 수 출력
 *
 *  사용한 문법 / 개념
 *    - 클래스, 객체, 생성자
 *    - 캡슐화(private 필드 + getter)
 *    - 인터페이스 Comparable<T> (Book 정렬 기준 제공)
 *    - 제네릭 클래스 / 제네릭 컬렉션
 *      · Map<Integer, Book>, Map<Integer, Member>
 *      · Map<Integer, Integer> (bookId -> memberId)
 *      · List<Book>, List<Member>
 *    - 컬렉션 프레임워크
 *      · HashMap, ArrayList, Collections.sort
 *    - Scanner를 이용한 콘솔 기반 메뉴 시스템
 *
 *  실행 방법 (터미널 기준)
 *    1) javac BookManagement.java
 *    2) java BookManagement
 *
 * =========================================
 */

import java.util.ArrayList;   // 동적 크기의 리스트 구현체 ArrayList 사용
import java.util.Collections; // 정렬을 위해 Collections 유틸리티 클래스 사용
import java.util.Comparator;  // 사용자 정의 정렬 기준을 만들기 위한 Comparator 인터페이스
import java.util.HashMap;     // 키-값 저장을 위한 HashMap 사용
import java.util.List;        // List 인터페이스 타입 사용
import java.util.Map;         // Map 인터페이스 타입 사용
import java.util.Scanner;     // 콘솔 입력을 위한 Scanner 클래스 사용

/*
 * Book 클래스
 *  - 도서 한 권에 대한 정보를 담는 클래스
 *  - Comparable<Book> 인터페이스를 구현해서
 *    제목 오름차순 정렬 기준을 제공한다.
 */
class Book implements Comparable<Book> {

    // 도서 고유 ID (정수)
    private int bookId;

    // 도서 제목
    private String title;

    // 도서 저자
    private String author;

    // 출판 연도 (예: 2020)
    private int year;

    /*
     * Book 생성자
     *  - 도서 ID, 제목, 저자, 연도를 받아 객체를 초기화한다.
     */
    public Book(int bookId, String title, String author, int year) {
        this.bookId = bookId;     // 매개변수 bookId 를 필드에 저장
        this.title = title;       // 매개변수 title 을 필드에 저장
        this.author = author;     // 매개변수 author 를 필드에 저장
        this.year = year;         // 매개변수 year 를 필드에 저장
    }

    // 각 필드에 접근하기 위한 getter 메서드들

    public int getBookId() {
        return bookId;            // 도서 ID 반환
    }

    public String getTitle() {
        return title;             // 도서 제목 반환
    }

    public String getAuthor() {
        return author;            // 도서 저자 반환
    }

    public int getYear() {
        return year;              // 출판 연도 반환
    }

    /*
     * Comparable<Book> 인터페이스의 compareTo 구현
     *  - this.title 과 다른 Book의 title 을 비교하여
     *    제목 기준 오름차순 정렬이 가능하도록 한다.
     */
    @Override
    public int compareTo(Book other) {
        // String 의 compareTo 메서드를 사용해 제목을 사전식으로 비교한다.
        return this.title.compareTo(other.title);
    }

    /*
     * toString 메서드
     *  - Book 객체를 사람이 보기 좋은 문자열 형태로 바꾼다.
     *  - 출력 시 자동으로 호출되어 도서 정보를 보기 좋게 보여준다.
     */
    @Override
    public String toString() {
        return "Book{ID=" + bookId +
                ", 제목='" + title + '\'' +
                ", 저자='" + author + '\'' +
                ", 연도=" + year +
                '}';
    }
}

/*
 * Member 클래스
 *  - 도서관 회원 정보를 저장하는 클래스
 */
class Member {

    // 회원 고유 ID (정수)
    private int memberId;

    // 회원 이름
    private String name;

    // 회원 연락처 (전화번호 등)
    private String phone;

    /*
     * Member 생성자
     *  - 회원 ID, 이름, 연락처를 받아서 필드를 초기화한다.
     */
    public Member(int memberId, String name, String phone) {
        this.memberId = memberId; // 매개변수 memberId를 필드에 저장
        this.name = name;         // 매개변수 name을 필드에 저장
        this.phone = phone;       // 매개변수 phone을 필드에 저장
    }

    // 각 필드에 대한 getter 메서드

    public int getMemberId() {
        return memberId;          // 회원 ID 반환
    }

    public String getName() {
        return name;              // 회원 이름 반환
    }

    public String getPhone() {
        return phone;             // 연락처 반환
    }

    /*
     * toString 메서드
     *  - Member 객체를 보기 좋은 문자열로 표현
     */
    @Override
    public String toString() {
        return "Member{ID=" + memberId +
                ", 이름='" + name + '\'' +
                ", 연락처='" + phone + '\'' +
                '}';
    }
}

/*
 * Library 클래스
 *  - 실제 도서 관리, 회원 관리, 대출 관리 로직을 담당하는 클래스
 *  - 내부에서 컬렉션(Map, List)을 사용해서 데이터를 관리한다.
 */
class Library {

    /*
     * 도서 목록 저장 컬렉션
     *
     *  - key   : 도서 ID (Integer)
     *  - value : Book 객체
     *
     *  HashMap<Integer, Book>
     *   · HashMap : 키에 대한 빠른 검색을 지원하는 컬렉션
     *   · Integer : 도서 ID
     *   · Book    : 도서 정보 객체
     */
    private Map<Integer, Book> books = new HashMap<Integer, Book>();

    /*
     * 회원 목록 저장 컬렉션
     *
     *  - key   : 회원 ID (Integer)
     *  - value : Member 객체
     */
    private Map<Integer, Member> members = new HashMap<Integer, Member>();

    /*
     * 대출 정보 저장 컬렉션
     *
     *  - key   : 도서 ID
     *  - value : 대출 중인 회원 ID
     *
     *  규칙:
     *   · 한 도서는 동시에 한 명에게만 대출 가능 → bookId 가 key 이므로 중복 안 됨
     *   · 한 회원은 여러 도서를 빌릴 수 있음 → value는 중복 가능
     */
    private Map<Integer, Integer> loans = new HashMap<Integer, Integer>();

    /*
     * 도서 등록 메서드
     *  - 도서 ID가 이미 존재하면 등록하지 않는다.
     */
    public void addBook(Book book) {
        int id = book.getBookId();        // 전달받은 Book 객체에서 ID를 꺼낸다.

        if (books.containsKey(id)) {      // 이미 같은 ID가 존재하는지 확인
            System.out.println("이미 존재하는 도서 ID입니다. 등록 실패: " + id);
            return;                       // 중복이면 메서드 종료
        }

        books.put(id, book);              // Map에 도서 등록
        System.out.println("도서 등록 완료: " + book);
    }

    /*
     * 도서 삭제 메서드
     *  - 해당 ID의 도서가 없으면 삭제 실패 메시지 출력
     *  - 대출 중인 책이면 삭제 불가 (간단한 규칙 추가)
     */
    public void removeBook(int bookId) {
        // 먼저 해당 도서가 존재하는지 확인
        if (!books.containsKey(bookId)) {
            System.out.println("해당 ID의 도서가 존재하지 않습니다: " + bookId);
            return;
        }

        // loans Map 에 bookId가 key로 존재하면 현재 대출 중이라는 의미
        if (loans.containsKey(bookId)) {
            System.out.println("현재 대출 중인 도서는 삭제할 수 없습니다.");
            return;
        }

        // 위 조건을 통과하면 실제 삭제 진행
        Book removed = books.remove(bookId); // Map에서 도서 삭제 후, 삭제된 Book을 반환받음
        System.out.println("도서 삭제 완료: " + removed);
    }

    /*
     * 전체 도서 목록 출력
     *  - books Map의 value들을 List로 모아 출력
     */
    public void printAllBooks() {
        if (books.isEmpty()) {                           // 도서가 하나도 없으면
            System.out.println("등록된 도서가 없습니다."); // 안내 메시지 출력
            return;
        }

        System.out.println("===== 전체 도서 목록 =====");

        // Map의 values()를 이용해 Book 객체들만 모아서 반복
        for (Book book : books.values()) {
            System.out.println(book);                    // Book의 toString() 결과 출력
        }
    }

    /*
     * 도서 제목 검색 기능
     *  - 특정 키워드를 제목에 포함하는 도서들을 찾는다.
     *  - 대소문자 구분 없이 검색하기 위해 모두 소문자로 변환하여 비교.
     */
    public void searchBooksByTitle(String keyword) {
        // 검색 키워드가 null일 경우를 방지하기 위해 간단히 체크
        if (keyword == null || keyword.length() == 0) {
            System.out.println("검색어가 비어 있습니다.");
            return;
        }

        String lowerKeyword = keyword.toLowerCase(); // 키워드를 소문자로 변환
        boolean found = false;                       // 검색 결과가 있었는지 표시하는 플래그

        System.out.println("===== 제목에 '" + keyword + "' 이(가) 포함된 도서 =====");

        // 모든 도서를 순회하면서 제목을 확인
        for (Book book : books.values()) {
            String lowerTitle = book.getTitle().toLowerCase(); // 도서 제목을 소문자로 변환
            if (lowerTitle.contains(lowerKeyword)) {           // 제목에 키워드가 포함되어 있다면
                System.out.println(book);                      // 해당 도서 출력
                found = true;                                  // 하나 이상 찾았음을 표시
            }
        }

        // 하나도 못 찾았으면 안내 메시지
        if (!found) {
            System.out.println("해당 키워드를 포함하는 도서를 찾을 수 없습니다.");
        }
    }

    /*
     * 회원 등록 메서드
     *  - 같은 회원 ID가 이미 존재하면 등록 실패
     */
    public void addMember(Member member) {
        int id = member.getMemberId();        // 회원 ID 가져오기

        if (members.containsKey(id)) {        // 이미 ID가 존재하는지 검사
            System.out.println("이미 존재하는 회원 ID입니다. 등록 실패: " + id);
            return;
        }

        members.put(id, member);              // Map에 회원 저장
        System.out.println("회원 등록 완료: " + member);
    }

    /*
     * 회원 삭제 메서드
     *  - 해당 회원이 현재 대출 중인 도서가 있다면 삭제 불가
     */
    public void removeMember(int memberId) {
        // members Map에 해당 회원이 존재하는지 먼저 확인
        if (!members.containsKey(memberId)) {
            System.out.println("해당 ID의 회원이 존재하지 않습니다: " + memberId);
            return;
        }

        // loans Map 을 순회하여 value가 memberId인 항목이 있는지 확인
        for (Integer loanMemberId : loans.values()) {
            if (loanMemberId == memberId) {
                System.out.println("현재 도서를 대출 중인 회원은 삭제할 수 없습니다.");
                return; // 하나라도 대출 중이면 삭제 중단
            }
        }

        // 대출 중이 아니면 안전하게 삭제
        Member removed = members.remove(memberId); // 삭제된 Member를 반환받음
        System.out.println("회원 삭제 완료: " + removed);
    }

    /*
     * 전체 회원 목록 출력 메서드
     */
    public void printAllMembers() {
        if (members.isEmpty()) {                           // 회원이 없으면
            System.out.println("등록된 회원이 없습니다."); // 안내 문구 출력
            return;
        }

        System.out.println("===== 전체 회원 목록 =====");

        // Map의 values()를 이용해 Member 객체들 순회
        for (Member member : members.values()) {
            System.out.println(member);
        }
    }

    /*
     * 도서 대출 메서드
     *  - bookId, memberId 를 받아서 대출을 처리한다.
     *  - 규칙
     *      · 도서, 회원이 존재해야 한다.
     *      · 이미 대출 중인 도서는 다시 대출 불가.
     */
    public void loanBook(int bookId, int memberId) {
        // 책 존재 여부 확인
        if (!books.containsKey(bookId)) {
            System.out.println("해당 ID의 도서가 존재하지 않습니다: " + bookId);
            return;
        }

        // 회원 존재 여부 확인
        if (!members.containsKey(memberId)) {
            System.out.println("해당 ID의 회원이 존재하지 않습니다: " + memberId);
            return;
        }

        // 이미 해당 도서가 대출 중인지 확인
        if (loans.containsKey(bookId)) {
            System.out.println("이미 대출 중인 도서입니다. 대출 실패.");
            return;
        }

        // 모든 조건 통과 → 대출 처리
        loans.put(bookId, memberId); // loans 맵에 bookId -> memberId 등록

        Book book = books.get(bookId);         // 방금 대출된 도서 객체 가져오기
        Member member = members.get(memberId); // 대출자 정보 가져오기

        System.out.println("대출 완료: 도서 " + book.getTitle()
                + " (ID=" + bookId + "), 회원 " + member.getName()
                + " (ID=" + memberId + ")");
    }

    /*
     * 도서 반납 메서드
     *  - bookId를 받아서 반납 처리
     */
    public void returnBook(int bookId) {
        // loans Map에 bookId가 존재하는지 확인
        if (!loans.containsKey(bookId)) {
            System.out.println("해당 도서는 현재 대출 중이 아닙니다: " + bookId);
            return;
        }

        // loans Map에서 bookId에 해당하는 대출 정보를 제거
        int memberId = loans.remove(bookId); // 삭제 후 대출자 ID를 반환받음

        Book book = books.get(bookId);         // 도서 정보
        Member member = members.get(memberId); // 회원 정보

        System.out.println("반납 완료: 도서 " + book.getTitle()
                + " (ID=" + bookId + "), 회원 " + member.getName()
                + " (ID=" + memberId + ")");
    }

    /*
     * 현재 대출 중인 모든 도서 목록을 출력하는 메서드
     */
    public void printAllLoans() {
        if (loans.isEmpty()) {                            // 대출 정보가 하나도 없으면
            System.out.println("현재 대출 중인 도서가 없습니다.");
            return;
        }

        System.out.println("===== 대출 현황 =====");

        // loans Map의 각 항목에 대해 반복
        for (Map.Entry<Integer, Integer> entry : loans.entrySet()) {
            int bookId = entry.getKey();       // 도서 ID
            int memberId = entry.getValue();   // 대출자 회원 ID

            Book book = books.get(bookId);       // 도서 정보 가져오기
            Member member = members.get(memberId); // 회원 정보 가져오기

            System.out.println("도서: " + book.getTitle() + " (ID=" + bookId + ")"
                    + " -> 대출자: " + member.getName() + " (ID=" + memberId + ")");
        }
    }

    /*
     * 정렬 1 : 제목 오름차순으로 도서 목록 출력
     *  - Book 클래스가 Comparable<Book> 을 구현했기 때문에
     *    Collections.sort(list)를 바로 사용할 수 있다.
     */
    public void printBooksSortedByTitle() {
        if (books.isEmpty()) {
            System.out.println("정렬할 도서가 없습니다.");
            return;
        }

        // Map에 저장된 도서들을 ArrayList로 복사
        List<Book> list = new ArrayList<Book>(books.values());

        // Book의 compareTo 기준(제목 오름차순)으로 정렬
        Collections.sort(list);

        System.out.println("===== 제목 오름차순 정렬 =====");
        for (Book book : list) {
            System.out.println(book);
        }
    }

    /*
     * 정렬 2 : 출판 연도 내림차순으로 도서 목록 출력
     *  - Comparator<Book> 익명 클래스를 사용해 정렬 기준을 정의
     */
    public void printBooksSortedByYearDesc() {
        if (books.isEmpty()) {
            System.out.println("정렬할 도서가 없습니다.");
            return;
        }

        // Map의 도서들을 List로 옮긴다.
        List<Book> list = new ArrayList<Book>(books.values());

        // Collections.sort 에 Comparator<Book>을 전달해
        // 연도 기준 내림차순 정렬을 수행한다.
        Collections.sort(list, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                // b2.year - b1.year 로 하면 값이 크면 b2가 앞에 온다 → 내림차순
                return b2.getYear() - b1.getYear();
            }
        });

        System.out.println("===== 출판연도 내림차순 정렬 =====");
        for (Book book : list) {
            System.out.println(book);
        }
    }

    /*
     * 통계 기능
     *  - 현재 대출 중인 도서 수 출력
     */
    public void printStatistics() {
        int loanCount = loans.size(); // loans Map의 크기가 곧 대출 중인 도서 수
        System.out.println("현재 대출 중인 도서 수: " + loanCount + "권");
    }
}

/*
 * BookManagement 클래스
 *  - 프로그램 실행 진입점
 *  - 콘솔 메뉴를 통해 Library 객체의 기능을 호출한다.
 */
public class BookManagement {

    /*
     * 메인 메뉴를 출력하는 보조 메서드
     *  - 보기 편하게 별도 메서드로 분리
     */
    private static void printMenu() {
        System.out.println();
        System.out.println("===== 도서 관리 프로그램 (BookManagement) =====");
        System.out.println("1. 도서 등록");
        System.out.println("2. 도서 삭제");
        System.out.println("3. 전체 도서 출력");
        System.out.println("4. 도서 제목 검색");
        System.out.println("5. 회원 등록");
        System.out.println("6. 회원 삭제");
        System.out.println("7. 전체 회원 출력");
        System.out.println("8. 도서 대출");
        System.out.println("9. 도서 반납");
        System.out.println("10. 대출 현황 출력");
        System.out.println("11. 제목 오름차순 정렬 출력");
        System.out.println("12. 출판연도 내림차순 정렬 출력");
        System.out.println("13. 통계 출력 (대출 중 도서 수)");
        System.out.println("0. 종료");
        System.out.print("메뉴 선택: ");
    }

    /*
     * main 메서드
     *  - 프로그램이 시작되는 지점
     */
    public static void main(String[] args) {

        // Library 객체 생성 (도서관 시스템 핵심 로직을 담당)
        Library library = new Library();

        // 콘솔 입력을 받기 위한 Scanner 객체 생성
        Scanner scanner = new Scanner(System.in);

        // 사용자가 0을 입력하여 종료하기 전까지 반복할 무한 루프
        while (true) {

            // 매 반복마다 메인 메뉴를 출력
            printMenu();

            // 사용자가 메뉴 번호를 입력
            int choice = -1; // 잘못된 초기값
            try {
                choice = Integer.parseInt(scanner.nextLine()); // 한 줄 읽어 정수로 변환
            } catch (NumberFormatException e) {
                // 숫자가 아닌 값을 입력한 경우 예외가 발생할 수 있으므로 간단히 처리
                System.out.println("숫자를 입력해주세요.");
                continue; // 반복문의 처음으로 돌아간다.
            }

            // 입력된 메뉴 번호에 따라 분기 처리
            if (choice == 0) {
                // 0번: 프로그램 종료
                System.out.println("프로그램을 종료합니다.");
                break; // while 루프 탈출
            } else if (choice == 1) {
                // 1번: 도서 등록
                System.out.print("도서 ID: ");
                int id = Integer.parseInt(scanner.nextLine());

                System.out.print("제목: ");
                String title = scanner.nextLine();

                System.out.print("저자: ");
                String author = scanner.nextLine();

                System.out.print("출판 연도: ");
                int year = Integer.parseInt(scanner.nextLine());

                // 입력받은 정보로 Book 객체 생성
                Book book = new Book(id, title, author, year);
                // Library에 등록 요청
                library.addBook(book);

            } else if (choice == 2) {
                // 2번: 도서 삭제
                System.out.print("삭제할 도서 ID: ");
                int id = Integer.parseInt(scanner.nextLine());
                library.removeBook(id);

            } else if (choice == 3) {
                // 3번: 전체 도서 출력
                library.printAllBooks();

            } else if (choice == 4) {
                // 4번: 도서 제목 검색
                System.out.print("검색할 키워드: ");
                String keyword = scanner.nextLine();
                library.searchBooksByTitle(keyword);

            } else if (choice == 5) {
                // 5번: 회원 등록
                System.out.print("회원 ID: ");
                int memberId = Integer.parseInt(scanner.nextLine());

                System.out.print("이름: ");
                String name = scanner.nextLine();

                System.out.print("연락처: ");
                String phone = scanner.nextLine();

                Member member = new Member(memberId, name, phone);
                library.addMember(member);

            } else if (choice == 6) {
                // 6번: 회원 삭제
                System.out.print("삭제할 회원 ID: ");
                int memberId = Integer.parseInt(scanner.nextLine());
                library.removeMember(memberId);

            } else if (choice == 7) {
                // 7번: 전체 회원 목록 출력
                library.printAllMembers();

            } else if (choice == 8) {
                // 8번: 도서 대출
                System.out.print("대출할 도서 ID: ");
                int bookId = Integer.parseInt(scanner.nextLine());

                System.out.print("대출자 회원 ID: ");
                int memberId = Integer.parseInt(scanner.nextLine());

                library.loanBook(bookId, memberId);

            } else if (choice == 9) {
                // 9번: 도서 반납
                System.out.print("반납할 도서 ID: ");
                int bookId = Integer.parseInt(scanner.nextLine());
                library.returnBook(bookId);

            } else if (choice == 10) {
                // 10번: 대출 현황 출력
                library.printAllLoans();

            } else if (choice == 11) {
                // 11번: 제목 오름차순 정렬 출력
                library.printBooksSortedByTitle();

            } else if (choice == 12) {
                // 12번: 출판연도 내림차순 정렬 출력
                library.printBooksSortedByYearDesc();

            } else if (choice == 13) {
                // 13번: 통계 기능
                library.printStatistics();

            } else {
                // 그 외 숫자: 잘못된 메뉴 번호
                System.out.println("잘못된 메뉴 번호입니다. 다시 선택해주세요.");
            }
        }

        // Scanner 자원 정리
        scanner.close();
    }
}
