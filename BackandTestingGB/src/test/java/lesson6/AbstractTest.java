package lesson6;



import com.github.javafaker.Faker;
import lesson5.util.LoggingInterceptor;
import lesson6.api.CategoryService;
import lesson6.api.ProductService;
import lesson6.db.dao.CategoriesMapper;
import lesson6.db.dao.ProductsMapper;
import lesson6.dto.Product;
import lesson6.utils.DbUtils;
import lesson6.utils.RetrofitUtils;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Retrofit;

public class AbstractTest {

    Integer id;
    static ProductsMapper productsMapper;
    static CategoriesMapper categoriesMapper;
    static Retrofit client;
    static ProductService productService;
    static CategoryService categoryService;
    Product product;
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        client = RetrofitUtils.getRetrofit();
        productService = client.create(ProductService.class);
        categoryService = client.create(CategoryService.class);
        productsMapper = DbUtils.getProductMapper();
        categoriesMapper = DbUtils.getCategoriesMapper();
    }

}
