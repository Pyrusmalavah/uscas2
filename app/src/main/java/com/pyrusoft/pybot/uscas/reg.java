package com.pyrusoft.pybot.uscas;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class reg extends Fragment {
    private TextInputEditText Name, UserName, UserPassword, Email;
    private Button BnRegister;

    public reg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg, container, false);
        UserName = view.findViewById(R.id.txt_reg_num);
        Email = view.findViewById(R.id.txt_email);
        Name = view.findViewById(R.id.txt_full_name);
        UserPassword = view.findViewById(R.id.txt_password);
        BnRegister = view.findViewById(R.id.bn_register);


        BnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegistration();
            }
        });
        return view;
    }

    public void performRegistration() {
        String name = Name.getText().toString();
        String reg_num = UserName.getText().toString();
        String password = UserPassword.getText().toString();
        String mail = Email.getText().toString();

        Call<User> call = HomeActivity.apiInterface.performRegistration(name, reg_num, password, mail);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("ok")) {
                    HomeActivity.prefConfig.displayToast("Registration Successful");
                } else if (response.body().getResponse().equals("exist")) {
                    HomeActivity.prefConfig.displayToast("A user with that registration already exists");
                } else if (response.body().getResponse().equals("error")) {
                    HomeActivity.prefConfig.displayToast("Unable to connect to server..!");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        UserName.setText("");
        Email.setText("");
        Name.setText("");
        UserPassword.setText("");


    }

}
