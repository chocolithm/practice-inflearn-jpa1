package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final MemberRepository memberRepository;

        public void dbInit1() {
            Member member = getMember("userA", "Seoul", "1", "1111");
            em.persist(member);

            Book book1 = getBook("jpaBookA", 10000, 100);
            em.persist(book1);

            Book book2 = getBook("jpaBookB", 20000, 80);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = getMember("userB", "Busan", "2", "1112");
            em.persist(member);

            Book book1 = getBook("springBookA", 15000, 90);
            em.persist(book1);

            Book book2 = getBook("springBookB", 25000, 110);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 15000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 25000, 2);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member getMember(String username, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(username);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book getBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
