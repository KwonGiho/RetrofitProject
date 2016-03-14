package com.example.kwongiho.retrofitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.sendBtn) Button sendBtn;
    @Bind(R.id.listView) ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).build();

        GitHubService gitHubService = retrofit.create(GitHubService.class);

        final Call<List<Contributor>> call = gitHubService.repoContributors("square","retrofit");
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.enqueue(new Callback<List<Contributor>>() {
                    @Override
                    public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {

                        if(response.isSuccess()) {
                            List<Contributor> listOfContributor = response.body();
                            ArrayList<Contributor> contributorArrayList = new ArrayList<Contributor>();
                            for(Contributor contributor : listOfContributor) {
                                contributorArrayList.add(contributor);
                            }
                            RepoItemsAdapter repoItemsAdapter = new RepoItemsAdapter(getApplicationContext(),contributorArrayList);
                            listView.setAdapter(repoItemsAdapter);

                        }
                        else
                            Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<Contributor>> call, Throwable t) {

                    }
                });
            }
        });

        //call.cancel(); //이건 취소할 때.
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
