package com.pyrusoft.pybot.uscas;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class login extends Fragment {


    private TextInputEditText RegNum;
    private TextInputEditText Password;
    private Button LoginBn;
    private TextView RegText;

    OnLoginFormActivityListener loginFormActivityListener;

    public interface OnLoginFormActivityListener {
        public void performRegister();

        public void performLogin(String name);

    }

    public login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        RegText = view.findViewById(R.id.register_txt);
        RegNum = view.findViewById(R.id.reg_num);
        Password = view.findViewById(R.id.txt_pass);
        LoginBn = view.findViewById(R.id.login_bn);

        LoginBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfomLogin();
            }
        });


        RegText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFormActivityListener.performRegister();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;
    }





    /*
        txtRegNum = view.findViewById(R.id.text_reg_number);
        txtPassword = view.findViewById(R.id.text_password);



    private boolean validateRegNum() {
        String regInput = txtRegNum.getEditText().getText().toString().trim();
        if (regInput.isEmpty()) {
            txtRegNum.setError("Field can't be empty");
            return false;
        } else if (regInput.length() > 8) {
            txtRegNum.setError("Registration Number is too long");
            return false;
        } else {
            txtRegNum.setError(null);
            txtRegNum.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validatePass() {
        String regInput = txtPassword.getEditText().getText().toString().trim();
        if (regInput.isEmpty()) {
            txtPassword.setError("Field can't be empty");
            return false;
        } else {
            txtPassword.setError(null);
            txtPassword.setErrorEnabled(false);
            return true;
        }
    }


    public void confirmInput(View v) {

        if(validateRegNum()| validatePass()){
            return;
        }


    }
*/

    private void perfomLogin() {
        String username = RegNum.getText().toString();
        String password = Password.getText().toString();

        Call<User> call = HomeActivity.apiInterface.performUserLogin(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok")) {
                    HomeActivity.prefConfig.writeLoginStatus(true);
                    loginFormActivityListener.performLogin(response.body().getName());
                } else if (response.body().getResponse().equals("failed")) {
                    HomeActivity.prefConfig.displayToast("Login failed, please try again..!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        RegNum.setText("");
        Password.setText("");


    }

}
