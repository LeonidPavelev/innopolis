package innopolis;

import config.JDBCTemplateLink;
import entity.ProductsEntity;
import entity.UsersEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import repository.OrdersRepository;
import repository.ProductsRepository;
import repository.UsersRepository;
import repository.impl.OrdersRepositoryImpl;
import repository.impl.ProductsRepositoryImpl;
import repository.impl.UsersRepositoryImpl;

import java.sql.Timestamp;
import java.util.List;

public class Main {
    private static final ProductsRepository productsRepository = new ProductsRepositoryImpl();

    private static final UsersRepository usersRepository = new UsersRepositoryImpl();

    private static final OrdersRepository ordersRepository = new OrdersRepositoryImpl();

    public static void main(String[] args) {
        System.out.println(productsRepository.findAll());
        System.out.println(usersRepository.findAll());
        System.out.println(ordersRepository.findAll());
    }




}