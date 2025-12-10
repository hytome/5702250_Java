# 📘 BookManagement — 도서 관리 프로그램
### 컬렉션 프레임워크 + 제네릭 + 클래스(OOP) 기반 콘솔 프로그램
**작성자: 허현 **

---

## 📌 1. 프로젝트 개요

이 프로그램은 **Java OOP, 컬렉션 프레임워크(Map, List), 제네릭(Generic)** 을 활용해 구현한  
**콘솔 기반 도서 관리 시스템(Library Management System)** 입니다.

프로그램에서는 다음 기능을 제공합니다:

- 도서(Book) 등록 / 삭제 / 조회 / 검색
- 회원(Member) 등록 / 삭제 / 조회
- 대출 / 반납 기능
- 제목 오름차순 정렬, 출판 연도 내림차순 정렬
- 통계 기능(현재 대출 중인 도서 수)

이 프로젝트는 실제 도서관 업무 처리를 모델링하면서  
**자바의 실제 활용 패턴을 학습하기 위한 교육용 프로젝트**입니다.

---

## 📂 프로그램 구조 (Structure Overview)

### BookManagement.java

- **class Book implements Comparable<Book>**
    - 도서 정보 저장 (bookId, title, author, year)
    - compareTo() 오버라이드 → 제목 오름차순 정렬 기준 제공

- **class Member**
    - 회원 정보 저장 (memberId, name, phone)

- **class Library**
    - `Map<Integer, Book> books` : 도서 목록
    - `Map<Integer, Member> members` : 회원 목록
    - `Map<Integer, Integer> loans` : 대출 정보 (도서ID → 회원ID)
    - 도서 관리 기능 (등록/삭제/조회/검색)
    - 회원 관리 기능 (등록/삭제/조회)
    - 대출/반납 기능
    - 정렬 기능 (제목 오름차순, 연도 내림차순)
    - 통계 기능 (현재 대출 중 도서 수)

- **public class BookManagement**
    - main() : 프로그램 실행 시작점
    - 콘솔 메뉴 출력(printMenu)
    - Library 객체 호출 및 사용자 입력 처리
---

## 🧠 3. 사용한 주요 자바 문법

### ✔ 컬렉션(Collection Framework)
- `HashMap<Integer, Book>`
- `HashMap<Integer, Member>`
- `HashMap<Integer, Integer>` (대출 상태)
- `ArrayList<Book>`
- `Collections.sort()`

### ✔ 제네릭(Generic)
- 제네릭 Map, List 사용
- 타입 안정성 확보 → 캐스팅 필요 없음
- `<T extends Comparable<T>>` 구조 활용

### ✔ 인터페이스 Comparable
Book 클래스가 `Comparable<Book>`을 구현하여  
**제목 오름차순 정렬 기준(compareTo)** 제공.

### ✔ 익명 클래스(Comparator)
```java
Collections.sort(list, new Comparator<Book>() { ... });