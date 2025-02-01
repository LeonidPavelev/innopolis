package ru.innopolis;

import ru.innopolis.repository.OrderItemsRepository;
import ru.innopolis.repository.OrdersRepository;
import ru.innopolis.repository.ProductsRepository;
import ru.innopolis.repository.UsersRepository;
import ru.innopolis.repository.impl.OrderItemsRepositoryImpl;
import ru.innopolis.repository.impl.OrdersRepositoryImpl;
import ru.innopolis.repository.impl.ProductsRepositoryImpl;
import ru.innopolis.repository.impl.UsersRepositoryImpl;

public class Application {
    private static final ProductsRepository productsRepository = new ProductsRepositoryImpl();

    private static final UsersRepository usersRepository = new UsersRepositoryImpl();

    private static final OrdersRepository ordersRepository = new OrdersRepositoryImpl();

    private static final OrderItemsRepository orderItemsRepository = new OrderItemsRepositoryImpl();

    public static void main(String[] args) {
        System.out.println(productsRepository.findAll());
        System.out.println(usersRepository.findAll());
        System.out.println(ordersRepository.findAll());
        System.out.println(orderItemsRepository.findAll());
    }




}