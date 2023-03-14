package lesson6;

import lesson6.db.model.Categories;
import lesson6.dto.Category;
import lesson6.utils.DbUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryServiceTest extends AbstractTest {

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response<Category> getCategoryByIdResponse = categoryService.getCategoryById(1).execute();

        assertThat(getCategoryByIdResponse.isSuccessful(), CoreMatchers.is(true));
        assertThat(getCategoryByIdResponse.body().getId(), equalTo(1));
        assertThat(getCategoryByIdResponse.body().getTitle(), equalTo("Food"));

        Categories category = DbUtils.selectCategoryById(categoriesMapper, 1L);

        assertThat(category.getId(), equalTo(1L));
        assertThat(category.getTitle(), equalTo("Food") );
    }

    @SneakyThrows
    @Test
    void getCategoryByIdNegativeTest() {
        Response<Category> getCategoryByIdResponse = categoryService.getCategoryById(0).execute();
        assertThat(getCategoryByIdResponse.isSuccessful(), CoreMatchers.is(false));
        assertThat(getCategoryByIdResponse.code(), equalTo(404));
    }
}
