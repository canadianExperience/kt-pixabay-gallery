package com.me.kt_pixabay_gallery.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.me.kt_pixabay_gallery.R
import com.me.kt_pixabay_gallery.apimanager.api.RetrofitAPI
import com.me.kt_pixabay_gallery.repository.PictureRepository
import com.me.kt_pixabay_gallery.repository.PictureRepositoryInterface
import com.me.kt_pixabay_gallery.roomdb.PictureDao
import com.me.kt_pixabay_gallery.roomdb.PictureDataBase
import com.me.kt_pixabay_gallery.util.Util.BASE_URL
import com.me.kt_pixabay_gallery.util.Util.getProgressDrawable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDataBase(@ApplicationContext context: Context)
    = Room.databaseBuilder(
        context,
        PictureDataBase::class.java,
        "picture.db"
        ).build()

    @Singleton
    @Provides
    fun injectDao(dataBase: PictureDataBase) = dataBase.pictureDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)

    }

    @Singleton
    @Provides
    fun injectNormalRepository(dao: PictureDao, api: RetrofitAPI) = PictureRepository(dao, api) as PictureRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(getProgressDrawable(context))
                .error(R.drawable.ic_launcher_foreground)
        )
}