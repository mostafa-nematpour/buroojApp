package ir.burooj.basij;


import java.util.List;

import ir.burooj.basij.apiClass.CompleteProfile;
import ir.burooj.basij.apiClass.EducationalCalendar;
import ir.burooj.basij.apiClass.Event;
import ir.burooj.basij.apiClass.GetUser;
import ir.burooj.basij.apiClass.GroupL;
import ir.burooj.basij.apiClass.Response;
import ir.burooj.basij.apiClass.Servis1;
import ir.burooj.basij.apiClass.Servis2;
import ir.burooj.basij.apiClass.AppDetails;
import ir.burooj.basij.apiClass.Like;
import ir.burooj.basij.apiClass.Register;
import ir.burooj.basij.apiClass.generatePhoneToken;
import ir.burooj.basij.apiClass.verifyPhone;
import kotlin.jvm.internal.markers.KMutableList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

    //پست ها
    @FormUrlEncoded
    @POST("posts.php")
    Call<List<Item>> getPosts(@Field("user_id") String userId, @Field("page_number") int pageNumber,
                              @Field("token") String token, @Field("writerId") String id);

    //نویسنده ها
    @FormUrlEncoded
    @POST("writers.php")
    Call<List<GroupL>> getWriters(@Field("user_id") String userId,
                                  @Field("token") String token);

    @FormUrlEncoded
    @POST("writer.php")
    Call<GroupL> getWriter(@Field("user_id") String userId,
                                 @Field("token") String token,
                                 @Field("writer_id") String writerId);


    @GET("assets/EducationalCalendar.json")
    Call<List<EducationalCalendar>> getEducationalCalendars();

    //مراسم ها
    @FormUrlEncoded
    @POST("event/events.php")
    Call<List<Event>> getEvents(@Field("token") String token, @Field("user_id") String userId,
                                @Field("page_number") int pageNumber);

    //مراسم ها / ثبت نام
    @FormUrlEncoded
    @POST("event/registerEvent.php")
    Call<Response> registerEvent(@Field("token") String token, @Field("userId") String userId,
                                 @Field("eventId") String eventId);

    //مراسم ها / گرفتن مراسم
    @FormUrlEncoded
    @POST("event/getEvent.php")
    Call<Event> getEvent(@Field("userId") String userId, @Field("token") String token,
                         @Field("eventId") String eventId);

    //ثبت نام/شماره دادن
    @FormUrlEncoded
    @POST("generatePhoneToken.php")
    Call<generatePhoneToken> generatePhone(@Field("phoneNumber") String phoneNumber,
                                           @Field("mobile") String mobile);

    //ثبت نام/چک شماره
    @FormUrlEncoded
    @POST("verifyPhone.php")
    Call<verifyPhone> verifyPhoneSms(@Field("token") String token, @Field("code") String code,
                                     @Field("osApi") int osApi, @Field("model") String model);

    //ثبت نام /نهایی
    @FormUrlEncoded
    @POST("register.php")
    Call<Register> register(@Field("token") String token, @Field("id") int id,
                            @Field("osApi") int osApi, @Field("model") String model,
                            @Field("name") String name, @Field("lastname") String lastName,
                            @Field("brand") String brand,@Field("invitation_code") String invitationCode);

    //کاربر / لایک
    @FormUrlEncoded
    @POST("like.php")
    Call<Like> like(@Field("user_id") String userId, @Field("post_id") String postId,
                    @Field("token") String token);

    // اپ / اطلاعات اولیه
    @GET("app-details.php")
    Call<AppDetails> details();

    // کاربر / گرفتن اکانت
    @FormUrlEncoded
    @POST("getUser.php")
    Call<GetUser> getUser(@Field("user_id") String userId, @Field("token") String token);

    // کاربر / تکمیل اکانت
    @FormUrlEncoded
    @POST("completeProfile.php")
    Call<CompleteProfile> completeProfile(@Field("user_id") String userId,
                                          @Field("token") String token, @Field("name") String name,
                                          @Field("lastname") String lastname,
                                          @Field("reshte") int reshte,
                                          @Field("shomareDaneshjouyi") String shomareDaneshjouyi);

    //1 ابزار / گرفتن سرویس / الغدیر
    @GET("services/alghadir/servis1.json")
    Call<Servis1> alghadirServis();

    //2 ابزار / گرفتن سرویس / الغدیر
    @GET("services/alghadir/servis2.json")
    Call<List<Servis2>> alghadirServis2();


    //1 ابزار / گرفتن سرویس / سرخنکلا
    @GET("services/sorkhankolate/servis1.json")
    Call<Servis1> sorkhankolateServis();

    //2 ابزار / گرفتن سرویس / سرخنکلا
    @GET("services/sorkhankolate/servis2.json")
    Call<List<Servis2>> sorkhankolateServis2();

    @Streaming
    @GET
    Call<ResponseBody> downloadBurooj(@Url String fileUrl);
}