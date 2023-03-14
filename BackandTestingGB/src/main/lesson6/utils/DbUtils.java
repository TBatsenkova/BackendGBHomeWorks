package lesson6.utils;

import com.github.javafaker.Faker;
import lesson6.db.dao.CategoriesMapper;
import lesson6.db.dao.ProductsMapper;
import lesson6.db.model.Categories;
import lesson6.db.model.CategoriesExample;
import lesson6.db.model.Products;
import lesson6.db.model.ProductsExample;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

public class DbUtils {
    public static String resource = "mybatis-config.xml";

    static Faker faker = new Faker();

    private static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory;
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        return sqlSessionFactory.openSession(true);
    }

    @SneakyThrows
    public static CategoriesMapper getCategoriesMapper() {
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    @SneakyThrows
    public static ProductsMapper getProductMapper() {
        return getSqlSession().getMapper(ProductsMapper.class);
    }

    public static void createNewCategory(CategoriesMapper categoriesMapper) {
        Categories newCategory = new Categories();
        newCategory.setTitle(faker.food().fruit());
        categoriesMapper.insert(newCategory);
    }

    public static Categories selectCategoryById(CategoriesMapper categoriesMapper, Long id) {
        return categoriesMapper.selectByPrimaryKey(id);
    }

    public static List<Categories> selectAllCategory(CategoriesMapper categoriesMapper) {
        return categoriesMapper.selectByExample(new CategoriesExample());
    }

    public static Integer countCategories(CategoriesMapper categoriesMapper) {
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        return Math.toIntExact(categoriesCount);
    }

    public static Integer countProducts(ProductsMapper productsMapper) {
        long products = productsMapper.countByExample(new ProductsExample());
        return Math.toIntExact(products);
    }

    public static void updateProductById(ProductsMapper productsMapper, Products product) {
        productsMapper.updateByPrimaryKey(product);
    }

    public static void deleteProductById(ProductsMapper productsMapper, Integer id) {
        productsMapper.deleteByPrimaryKey(Long.valueOf(id));
    }

    public static void deleteAllProducts(ProductsMapper productsMapper) {
        productsMapper.selectByExample(new ProductsExample());
    }

    public static Products selectProductById(ProductsMapper productsMapper, Long id) {
        return productsMapper.selectByPrimaryKey(id);
    }

    public static List<Products> selectAllProducts(ProductsMapper productsMapper) {
        return productsMapper.selectByExample(new ProductsExample());
    }
}

