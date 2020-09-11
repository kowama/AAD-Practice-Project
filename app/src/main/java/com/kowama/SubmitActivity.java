package com.kowama;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kowama.utils.InterfaceAPI;
import com.kowama.utils.RetrofitCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmitActivity extends AppCompatActivity  {
    private Toolbar toolbar ;
    private EditText editTextLastName;
    private EditText editTextFistName ;
    private EditText editTextEmail ;
    private EditText editTextLink ;
    private Button buttonSubmit ;
    private LinearLayout linearLayoutFormContainer;
    AlertDialog confirmDialog ;
    ProgressDialog progressDialog ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        toolbar = findViewById(R.id.submit_project_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrocw);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editTextLastName = findViewById(R.id.submit_editext_last_name);
        editTextFistName = findViewById(R.id.submit_editext_first_name);
        editTextEmail = findViewById(R.id.submit_editext_email);
        editTextLink = findViewById(R.id.submit_editext_project_link);
        buttonSubmit = findViewById(R.id.submit_button_submit);
        linearLayoutFormContainer = findViewById(R.id.submit_form_container);
    }


    @Override
    protected void onResume() {
        super.onResume();
        buttonSubmit.setOnClickListener(submitButtonClicked);

    }

    private View.OnClickListener submitButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String firstName = editTextFistName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String email= editTextEmail.getText().toString();
            String project_link = editTextLink.getText().toString();

           linearLayoutFormContainer.setVisibility(View.INVISIBLE);
           showConfirmAndSubmitDialog(firstName, lastName, email, project_link);
        }
    };

    private void showSuccessfullySubmit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View  view = LayoutInflater.from(this)
                .inflate(R.layout.submit_success_dialog, null, false);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void showFailedSubmit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View  view = LayoutInflater.from(this)
                .inflate(R.layout.submit_failed_dialog, null, false);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void showConfirmAndSubmitDialog(final String firstName, final String lastName, final String email, final String project_link){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View  view = LayoutInflater.from(this)
                .inflate(R.layout.submit_confirm_dialog, null, false);
        builder.setView(view);
        Button confirm =  view.findViewById(R.id.button_submit_confirm);
        LinearLayout cancel = view.findViewById(R.id.cancel_parent_submission);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitProject(firstName, lastName, email, project_link);
                progressDialog = new ProgressDialog(SubmitActivity.this);
                progressDialog.setTitle("Project Uploading");
                progressDialog.setMessage("Wait util project submitted !");
                progressDialog.show();
                if(confirmDialog!=null) confirmDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutFormContainer.setVisibility(View.VISIBLE);
                if(confirmDialog!=null) confirmDialog.dismiss();
            }
        });
        confirmDialog = builder.create();
        confirmDialog.setCancelable(false);
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmDialog.show();
    }

    private void submitProject(String firstName, String lastName, String email, String project_link){
        Retrofit  retrofit = RetrofitCreator.getRetrofitInstance() ;
        final InterfaceAPI api = retrofit.create(InterfaceAPI.class);
        Call<Void> call = api.submitProject(firstName, lastName,email,project_link);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    showSuccessfullySubmit();
                    Log.d("API", response.toString());
                }else{
                    showFailedSubmit();
                    linearLayoutFormContainer.setVisibility(View.VISIBLE);
                    Log.d("API", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
               progressDialog.dismiss();
            }
        });
    }


}