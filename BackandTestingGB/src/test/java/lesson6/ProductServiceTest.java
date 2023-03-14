package lesson6;


import lesson6.db.model.Products;
import lesson6.dto.Product;
import lesson6.utils.DbUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.apache.ibatis.session.SqlSession;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class ProductServiceTest extends AbstractTest {

    Product newProduct;
    Product modifedProduct;
    Product productWithNullId;
    Integer id;

    @BeforeEach
    void setUp() {
        newProduct = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    @SneakyThrows
    void createProductInFoodCategoryPositiveTest() {
        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        Response<Product> response = productService.createProduct(newProduct).execute();
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore + 1));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo(newProduct.getTitle()));
        assertThat(response.body().getCategoryTitle(), equalTo(newProduct.getCategoryTitle()));
        assertThat(response.body().getPrice(), equalTo(newProduct.getPrice()));
    }

    @Test
    @SneakyThrows
    void modifyProductPositiveTest() {
        newProduct.setTitle("test");
        newProduct.setId(1);
        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        Response<Product> response = productService.modifyProduct(newProduct).execute();
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);

        assertThat(countProductsAfter, equalTo(countProductsBefore));

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo("test"));
        assertThat(response.body().getCategoryTitle(), equalTo(newProduct.getCategoryTitle()));
        assertThat(response.body().getPrice(), equalTo(newProduct.getPrice()));

        Products product = DbUtils.selectProductById(productsMapper, Long.valueOf(response.body().getId()));
        assertThat(product.getPrice(), equalTo(newProduct.getPrice()));
        assertThat(product.getTitle(), equalTo("test"));
    }

    @Test
    @SneakyThrows
    void createProductInFoodCategoryNegativeTest() {
        newProduct.setId(0);
        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        Response<Product> response = productService.createProduct(newProduct).execute();
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(400));
        assertThat(countProductsAfter, equalTo(countProductsBefore));
    }

    @Test
    @SneakyThrows
    void getProductByIdTest() {
        Response<Product> response = productService.createProduct(newProduct).execute();
        assert response.body() != null;
        id = response.body().getId();
        response = productService.getProductById(id).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getPrice(), equalTo(newProduct.getPrice()));
        assertThat(response.body().getTitle(), equalTo(newProduct.getTitle()));
        assertThat(response.body().getCategoryTitle(), equalTo(newProduct.getCategoryTitle()));

        Products product = DbUtils.selectProductById(productsMapper, Long.valueOf(id));
        assertThat(product.getTitle(), equalTo(newProduct.getTitle()));
        assertThat(product.getPrice(), equalTo(newProduct.getPrice()));
        assertThat(product.getCategory_id(), equalTo(1L));
    }

    @Test
    void getAllProductsTest() {
        List<Products> allProducts = DbUtils.selectAllProducts(productsMapper);
        for (Products i : allProducts) {
            assertThat(i.getId().toString(), Matchers.matchesPattern("^[0-9]{1,5}"));
            assertThat(i.getTitle(), Matchers.is(notNullValue()));
            assertThat(i.getPrice(), Matchers.is(notNullValue()));
            assertThat(i.getCategory_id(), Matchers.is(notNullValue()));
        }
    }

    @Test
    @SneakyThrows
    void deleteProductTest() {
        Response<Product> createNewProductResponse = productService.createProduct(newProduct).execute();
        assert createNewProductResponse.body() != null;
        id = createNewProductResponse.body().getId();

        Integer countProductsBefore = DbUtils.countProducts(productsMapper);
        Response<ResponseBody> deleteProductResponse = productService.deleteProduct(id).execute();
        Integer countProductsAfter = DbUtils.countProducts(productsMapper);
        assertThat(deleteProductResponse.isSuccessful(), CoreMatchers.is(true));
        assertThat(countProductsAfter, equalTo(countProductsBefore - 1));

        Response<Product> getProductAfterDeleteResponse = productService.getProductById(id).execute();
        assertThat(getProductAfterDeleteResponse.code(), equalTo(404));
    }

}

