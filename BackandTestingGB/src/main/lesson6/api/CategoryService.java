package lesson6.api;

import lesson6.dto.Category;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("categories/{id}")
    Call<Category> getCategoryById(@Path("id") int id);
}
