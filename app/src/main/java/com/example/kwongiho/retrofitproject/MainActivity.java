package com.example.kwongiho.retrofitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kwongiho.retrofitproject.model.dto.Contributor;

import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.sendBtn) Button sendBtn;
    @Bind(R.id.listView) RecyclerView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);

        /*
        RecycleView 순서~ 를 보기 전에 중요한 것.
        LayoutManager - 매우중요하지.
        Adapter – 기존의 ListView에서 사용하는 Adapter와 같은 개념으로 데이터와 아이템에 대한 View생성
        ViewHolder – 재활용 View에 대한 모든 서브 뷰를 보유
        ItemDecoration – 아이템 항목에서 서브뷰에 대한 처리
        ItemAnimation – 아이템 항목이 추가, 제거되거나 정렬될때 애니메이션 처리

        그리고
        지금부턴 순서를 볼까.
        1. Adapter와 LayoutManager를 new 한다.
        - 생성자들
        RecyclerView(Context context)
        RecyclerView(Context context, AttributeSet attrs) //AttributeSet은 LinearLayout일 경우 방향 말해주는 것. // 참고 - http://www.kmshack.kr/2014/10/android-recyclerview/
        RecyclerView(Context context, AttributeSet attrs, int defStyle)

        2. 요청을 날린다.
        3. 요청을 결과로 받은 애를 Adapter에 dynamic하게 set 해준다.
        4. 다 set 해준 뒤 notifyDataSetChanged 호출.

         */
        final RepoItemsAdapter repoItemsAdapter = new RepoItemsAdapter(getApplicationContext()); // Adapter 생성 했어!!!
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0); // LayoutManager도 set 했어!
        listView.setLayoutManager(linearLayoutManager); // setting!!!!!!!!!!!!!!!!!


        final Call<List<Contributor>> call = gitHubService.repoContributors("square","retrofit"); // 요청을 날릴 준비를 해야지!
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            call.clone().enqueue(new Callback<List<Contributor>>() { // 음..Call객체는 Clone하는 값이 저렴하데요. 매 요청마다 일레갈익셉션 방지! 매 새로운 요청!
                @Override
                public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {

                    if (response.isSuccess()) { // 스터디원 분들이 2.0부턴 이 메소드 호출해야 된다고 했는데 정확히 무슨 이유인지 잘 기억이 안난다..ㅠㅠ
                        List<Contributor> listOfContributor = response.body();
                        Log.d("listOfContributorSize", listOfContributor.size() + ""); // 몇개냐

                        for (Contributor contributor : listOfContributor) {
                            repoItemsAdapter.add(contributor);
                        }
                        runOnUiThread(new Runnable() {
                                          public void run() {
                                              //repoItemsAdapter.notifyDataSetChanged();
                                          }
                                      }
                        );
                    } else
                        Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<Contributor>> call, Throwable t) {

                }
            });
            }
        });
    }
    public interface GitHubService {
        @GET("/repos/{owner}/{repo}/contributors")
            // Call 객체는 요청/응답이 한 쌍으로써 요청을 보낼 때 execute을 호출하지 않았음을 보장해야 한다.
        Call<List<Contributor>> repoContributors(
                @Path("owner") String owner,
                @Path("repo") String repo
        );
        @GET
        Call<List<Contributor>> repoContributorPaginate(
                @Url String url
        );
    }
}
