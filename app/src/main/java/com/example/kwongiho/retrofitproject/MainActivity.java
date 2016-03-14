package com.example.kwongiho.retrofitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


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

    private TextView mainActivityTextView;
    private Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityTextView = (TextView)findViewById(R.id.mainActivityTextView);
        sendBtn = (Button)findViewById(R.id.sendBtn);

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
                            StringBuilder stringBuilder = new StringBuilder();
                            for(Contributor contributor : listOfContributor) {
                                stringBuilder.append(contributor.toString()+'\n');
                            }
                            mainActivityTextView.setText(stringBuilder.toString());

                        }
                        else
                            mainActivityTextView.setText("false");
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
    public class Contributor {
        private String login;
        private int id;

        public Contributor(int id, String login) {
            this.id = id;
            this.login = login;
        }

        public String getLogin() {

            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "login='" + login + '\'' +
                    ", id=" + id;
        }
    }
}
