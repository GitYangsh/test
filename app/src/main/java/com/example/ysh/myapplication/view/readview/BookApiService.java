/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ysh.myapplication.view.readview;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * https://github.com/JustWayward/BookReader
 *
 * @author yuyh.
 * @date 2016/8/3.
 */
public interface BookApiService {

    @GET("/book/recommend")
    Observable<Recommend> getRecomend(@Query("gender") String gender);

    @GET("/mix-atoc/{bookId}")
    Observable<BookMixAToc> getBookMixAToc(@Path("bookId") String bookId, @Query("view") String view);

    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    Observable<ChapterRead> getChapterRead(@Path("url") String url);
}