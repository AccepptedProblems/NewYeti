package com.main.newyeti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SearchList extends AppCompatActivity {

    TextView cancelSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        cancelSearch = findViewById(R.id.cancelSearch);

        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchList.this, ListFriends.class);
                startActivity(intent);
                finish();
            }
        });
    }
}