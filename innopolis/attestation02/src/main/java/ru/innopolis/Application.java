package ru.innopolis;

import ru.innopolis.entity.OrderItemsEntity;
import ru.innopolis.entity.ProductsEntity;
import ru.innopolis.entity.UsersEntity;
import ru.innopolis.repository.OrderItemsRepository;
import ru.innopolis.repository.OrdersRepository;
import ru.innopolis.repository.ProductsRepository;
import ru.innopolis.repository.UsersRepository;
import ru.innopolis.repository.impl.OrderItemsRepositoryImpl;
import ru.innopolis.repository.impl.OrdersRepositoryImpl;
import ru.innopolis.repository.impl.ProductsRepositoryImpl;
import ru.innopolis.repository.impl.UsersRepositoryImpl;

import java.util.List;

public class Application {
    private static final ProductsRepository productsRepository = new ProductsRepositoryImpl();

    private static final UsersRepository usersRepository = new UsersRepositoryImpl();

    private static final OrdersRepository ordersRepository = new OrdersRepositoryImpl();

    private static final OrderItemsRepository orderItemsRepository = new OrderItemsRepositoryImpl();

    public static void main(String[] args) {
        System.out.println(usersRepository.findAll());
        UsersEntity usersEntity = usersRepository.saveUser(UsersEntity.builder()
                .firstName("шик")
                .lastName("Клопов")
                .build());
        System.out.println(usersEntity);
        usersRepository.updateUser(UsersEntity.builder()
                .userId(2L)
                .firstName("[bg")
                .lastName("er5234")
                .build());
        System.out.println(usersRepository.findAll());

        ProductsEntity saveProduct = productsRepository.saveProduct(ProductsEntity.builder()
                .productName("dgsdg")
                .description("34242")
                .price(34.56)
                .stock(5)
                .build());
        System.out.println(saveProduct);
        productsRepository.updateProduct(ProductsEntity.builder()
                .productId(4L)
                .productName("435tert")
                .description("dsfgsdg")
                .price(3456.44)
                .stock(3)
                .build());



        System.out.println(productsRepository.findAll());
        System.out.println(ordersRepository.findAll());
        List<OrderItemsEntity> all = orderItemsRepository.findAll();
        System.out.println(all);
        List<OrderItemsEntity> allByQuantity = orderItemsRepository.findAllByQuantity(1);
        System.out.println(allByQuantity);
        List<ProductsEntity> allByStockGreaterThan = productsRepository.findAllByStockGreaterThan(50);
        System.out.println(allByStockGreaterThan);
        List<ProductsEntity> allSortedByPrice = productsRepository.findAllSortedByPrice();
        System.out.println(allSortedByPrice);
    }


}