# Trouble Shooting
프로젝트를 진행하면서 발생한 문제점들과 해결법을 서술합니다.

## Problem 1
- UnsupportedOperationException 발생
  - Arrays.asList의 결과인 ArrayList와 java.util.ArrayList는 다른 ArrayList이다.
  - 그래서 Arrays.asList의 결과를 ArrayList 객체가 들어갈 위치에 바로 넣을 수 없다.
  - 따라서 Arrays.asList의 결과를 addAll로 더해주는 형태로 작성해서 정상 동작한다.
  ```
  // 아래처럼 작성해야 정상 동작한다. 
  String[] rolesArr = {"ROLE_SUPPORTER", "ROLE_MAKER"};
  List<String> roles = new ArrayList<String>();
  roles.addAll(Arrays.asList(rolesArr));
  
  // 오류나는 경우
  String[] rolesArr = {"ROLE_SUPPORTER", "ROLE_MAKER"};
  List<String> roles = Arrays.asList(rolesArr);
  ```

## Problem 2
- JPA를 사용해 외래키로 데이터 조회할 때는 다음과 같은 형식으로 메서드명을 정해줘야 한다.
  - 참고) 사용되는 곳이 없는 메서드명을 정의하면 에러가 난다.
  ```
  // findBy + {fk가 pk인 부모 entity의 필드명} + {fk entity의 식별자 필드명}
  
  @Repository
  public interface MakerRepository extends JpaRepository<Maker, Long> {
  
    Optional<Maker> findByUser_UserId(long userId); // 외래키로 데이터 조회하기 위한 method 명 형식
  }
  ```
## Problem 3
- OneToMany, ManyToOne 관계 설정 시 에러 발생
  - Failed to initialize JPA EntityManagerFactory: mappedBy reference an unknown target entity property
    - mappedBy 뒤에는 products를 불러올 엔티티 테이블의 필드명을 적어줘야한다!
- Json으로 변환 과정에서 무한 참조가 발생할 수 있다.
    - @JsonIgnore 어노테이션을 붙여서 해결해준다.


## Problem 4
- lazyinitializationexception 발생
  - FetchType.LAZY가 설정된 필드를 컨트롤러 레벨에서 조회한 후,
    해당 데이터를 저장하려고 하면 조회한 시점에서 영속성 컨텍스트가 종료되어 버려서 저장할 수가 없다.
  - 이런 경우 @Transactional 처리를 해주거나 EAGER 타입으로 바꿔줘야 한다.
  - 내 코드에서는 EAGER 타입으로 바꿔주었다.
