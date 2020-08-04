package org.xm.lib.promote

import org.xm.lib.promote.data.RecommendBean
import retrofit2.http.GET
import rx.Observable

/**
 * Created by Allever on 2017/1/15.
 */

interface RetrofitService {
    @GET("data/allever/recommend/recommend.zh.json")
    fun getRecommendZh(): Observable<RecommendBean>

    @GET("data/allever/recommend/recommend.en.json")
    fun getRecommendEn(): Observable<RecommendBean>
}
