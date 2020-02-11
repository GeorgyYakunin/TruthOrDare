package com.example.truthordare.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.truthordare.R;
import com.example.truthordare.adapter.SimplestAdapter;
import com.example.truthordare.base.BaseActivity;
import com.example.truthordare.database.DBTruthDareAdapter;
import com.example.truthordare.model.TruthOrDare;
import com.example.truthordare.types.QuestionType;
import com.example.truthordare.utils.SoundUtils;
import com.example.truthordare.utils.StringUtils;
import com.example.truthordare.utils.Utils;

public class ActivityAddingDareOrTruth extends BaseActivity {

    public static class ViewHolder extends com.example.truthordare.adapter.SimplestAdapter.ViewHolder<TruthOrDare> {
        private TextView question;
        private ImageView removeBtn;

        public ViewHolder(View view) {
            super(view);
            this.question = (TextView) view.findViewById(R.id.tv_question);
            this.removeBtn = (ImageView) view.findViewById(R.id.iv_remove);
            Utils.setFont(this.question);
        }

        public void setData(final Context context, final TruthOrDare truthOrDare) {
            this.question.setText(truthOrDare.question);
            this.removeBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Utils.btnClickSound(context);
                    DBTruthDareAdapter.Delete_Question(context, truthOrDare.id);
                    ActivityAddingDareOrTruth.getListUpdate(context, ViewHolder.this.getAdapter(), truthOrDare.questionType);
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_adding_truth_or_dare);
        TextView title = (TextView) findViewById(R.id.txtTitle);
        Utils.setFont(title);
        final TextView etxPlayer = (TextView) findViewById(R.id.etxPlayer);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        ListView truthDareListView = (ListView) findViewById(R.id.lv_truthDares);
        final SimplestAdapter adapter = new SimplestAdapter(getApplicationContext(), ViewHolder.class, R.layout.item_cell_player, DBTruthDareAdapter.Get_Display_Questions(this, Utils.selectedQuestionType, Integer.valueOf(1)));
        truthDareListView.setAdapter(adapter);
        title.setText(getString(Utils.selectedQuestionType.getLbl()));
        btnAdd.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                try {
                    String truth = (String) StringUtils.subNulls(etxPlayer.getText().toString());
                    if (truth != null) {
                        SoundUtils.buttonOnClick(ActivityAddingDareOrTruth.this);
                        DBTruthDareAdapter.Add_Question(ActivityAddingDareOrTruth.this, new TruthOrDare(truth.trim(), Utils.selectedQuestionType, Integer.valueOf(1), null));
                        etxPlayer.setText("");
                        ActivityAddingDareOrTruth.getListUpdate(ActivityAddingDareOrTruth.this.getApplicationContext(), adapter, Utils.selectedQuestionType);
                    } else {
                        Toast.makeText(ActivityAddingDareOrTruth.this , "Please enter text" , Toast.LENGTH_LONG).show();
                    }
                    ((InputMethodManager) ActivityAddingDareOrTruth.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goBack(View view) {
        Utils.goBack(this);
    }

    public static void getListUpdate(Context context, SimplestAdapter adapter, QuestionType questionType) {
        adapter.setData(DBTruthDareAdapter.Get_Display_Questions(context, questionType, Integer.valueOf(1)));
        adapter.notifyDataSetChanged();
    }


}
