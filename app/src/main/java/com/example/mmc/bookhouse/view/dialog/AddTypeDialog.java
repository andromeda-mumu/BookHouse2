package com.example.mmc.bookhouse.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mmc.bookhouse.R;
import com.example.mmc.bookhouse.model.BookType;
import com.example.mmc.bookhouse.utils.ScreenUtils;
import com.example.mmc.bookhouse.utils.Toast;
import com.example.mmc.bookhouse.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：
 */

public class AddTypeDialog extends Dialog {
    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    public AddTypeDialog(@NonNull Context context) {
        super(context);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_add_type);
        ButterKnife.bind(this);

        getWindow().getAttributes().width = (int) (ScreenUtils.ScreenWidth*0.65);
    }

    @OnClick({R.id.btn_submit,R.id.iv_close})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_submit:
                submitType();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            default :
                break;

        }
    }

    private void submitType() {
        String newType = mEt.getText().toString().trim();
        if(Tools.notEmpty(newType)){
            Toast.show("添加成功");
            BookType bookType= new BookType();
            bookType.type=newType;
            bookType.save();
            dismiss();
        }
    }
}
