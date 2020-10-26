package ir.maherkala.maherkala.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.util.ArrayList;
import java.util.List;

import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getSearchCategory;

public class AdvancedSearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar my_toolbar;

    private String gheymat_az, gheymat_ta;
    private String gheymat_final, gheymat_final_number, foroshYaEjare = " ";
    private String gheymat_az_num, gheymat_ta_num;

    private String spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        ProgressBar progressBar = findViewById(R.id.progressBar4);

        //this is for get category Item spinner
        getSearchCategory getSearchCategory = new getSearchCategory();
        getSearchCategory.get_banners(AdvancedSearchActivity.this, progressBar);
        //end


        Toolbar();
        Basket();
        //RangeSeekBar();
        //SearchBtn(AdvancedSearchActivity.this);

    }


    private void RangeSeekBar() {

        // get seekbar from view
        CrystalRangeSeekbar rangeGheymat = findViewById(R.id.rangeGheymat);
        final TextView txtGheymat = findViewById(R.id.txtGheymat);
        rangeGheymat.setMaxValue(27);


        // set listener
        rangeGheymat.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                gheymat(String.valueOf(maxValue));
                gheymat_az = gheymat_final;
                gheymat_az_num = String.valueOf(maxValue);
                gheymat_final = null;
                gheymat(String.valueOf(minValue));
                gheymat_ta = gheymat_final;
                gheymat_ta_num = String.valueOf(minValue);
//                if (String.valueOf(minValue).equals("0")) {
//                    gheymat_ta = "بی نهایت";
//                } else {
                gheymat_ta = gheymat_ta + getString(R.string.currency);
//                }
//                if (String.valueOf(maxValue).equals("27") && String.valueOf(minValue).equals("0")) {
//
//                    txtGheymat.setText(" بدون محدودیت");
//
//                } else {
                Log.i("mohsenjamali", "valueChanged: " + foroshYaEjare);
                String gheymat_kol = foroshYaEjare + gheymat_az + " تا " + gheymat_ta;
                txtGheymat.setText(gheymat_kol);
//                }

            }
        });

        // set final value listener
        rangeGheymat.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                //   Log.d("mohsenjamali", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                gheymat(String.valueOf(minValue));
            }
        });

    }

    private void Toolbar() {

        setSupportActionBar(my_toolbar);
        my_toolbar = findViewById(R.id.toolbar);
        // RelativeLayout BasketLayout=findViewById(R.id.BasketLayout);
        //  BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("جستجوی پیشرفته");
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //  BackMethod();

            }
        });
        ImageView profile = findViewById(R.id.profile);
        profile.setVisibility(View.GONE);
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);

        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(AdvancedSearchActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(AdvancedSearchActivity.this)) {
        BadgeCounter.setVisibility(View.GONE);
        // }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);
        basket_icon_bar.setVisibility(View.GONE);
//        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(AdvancedSearchActivity.this, MainActivity.class);
//                intent.putExtra("NameActivity", "BasketFragment");
//                AdvancedSearchActivity.this.startActivity(intent);
//                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            }
//        });


    }

    public void spinner_category(final Context context, List<String> NameItems, final List<String> IdItems) {

        /**---Spinner-----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        final Spinner anva_melk = ((Activity) context).findViewById(R.id.Type);
        // Spinner click listener
        anva_melk.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>(NameItems);

        //  categories.addAll(NameItems);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(((Activity) context), R.layout.spinner_item, categories);

        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        anva_melk.setAdapter(dataAdapter);


        spinner = IdItems.get(anva_melk.getSelectedItemPosition());


        /**---SeekBar-----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


        CrystalRangeSeekbar rangeGheymat = ((Activity) context).findViewById(R.id.rangeGheymat);
        final TextView txtGheymat = ((Activity) context).findViewById(R.id.txtGheymat);
        rangeGheymat.setMaxValue(27);

        rangeGheymat.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                gheymat(String.valueOf(maxValue));
                gheymat_az = gheymat_final;
                gheymat_az_num = String.valueOf(maxValue);
                gheymat_final = null;
                gheymat(String.valueOf(minValue));
                gheymat_ta = gheymat_final;
                gheymat_ta_num = String.valueOf(minValue);
//                if (String.valueOf(minValue).equals("0")) {
//                    gheymat_ta = "بی نهایت";
//                } else {
                gheymat_ta = gheymat_ta + getString(R.string.currency);
//                }
//                if (String.valueOf(maxValue).equals("27") && String.valueOf(minValue).equals("0")) {
//
//                    txtGheymat.setText(" بدون محدودیت");
//
//                } else {
                // Log.i("mohsenjamali", "valueChanged: " + foroshYaEjare);
                String gheymat_kol = foroshYaEjare + gheymat_az + " تا " + gheymat_ta;
                txtGheymat.setText(gheymat_kol);
//                }

            }
        });

        // set final value listener
        rangeGheymat.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                //   Log.d("mohsenjamali", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                gheymat(String.valueOf(minValue));
            }
        });

        /**-----SearchBtn---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


        TextView searchBtn = ((Activity) context).findViewById(R.id.searchBtn);
        final EditText nameEd = ((Activity) context).findViewById(R.id.nameEdt);
        final SwitchCompat Switch = ((Activity) context).findViewById(R.id.switch1);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SnakBar snackItem = new SnakBar();

                if (nameEd.getText().toString().isEmpty()) {

                    snackItem.snakShow(((Activity) context), ((Activity) context).getString(R.string.fullText));

                } else if (nameEd.getText().toString().length() < 3) {

                    snackItem.snakShow(((Activity) context), ((Activity) context).getString(R.string.shortText));
                } else {


                    Intent intent = new Intent(context, ResultAdvancedSearchActivity.class);
                    intent.putExtra("CategoryID", IdItems.get(anva_melk.getSelectedItemPosition()));
                    intent.putExtra("Name", nameEd.getText().toString());
                    intent.putExtra("MinPrice", gheymatToNumber(gheymat_az_num));
                    intent.putExtra("MaxPrice", gheymatToNumber(gheymat_ta_num));


                    if (Switch.isChecked()) {
                        intent.putExtra("Switch", "true");
                    } else {
                        intent.putExtra("Switch", "false");
                    }

                    ((Activity) context).startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();


        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void gheymat(String gheymat) {
        switch (gheymat) {
            case "27":
                gheymat_final = "0";
                break;
            case "26":
                gheymat_final = "10 هزار";
                break;
            case "25":
                gheymat_final = "20 هزار";
                break;
            case "24":
                gheymat_final = "30 هزار";
                break;
            case "23":
                gheymat_final = "40 هزار";
                break;
            case "22":
                gheymat_final = "50 هزار";
                break;
            case "21":
                gheymat_final = "75 هزار";
                break;
            case "20":
                gheymat_final = "100 هزار";
                break;
            case "19":
                gheymat_final = "125 هزار";
                break;
            case "18":
                gheymat_final = "150 هزار";
                break;
            case "17":
                gheymat_final = "175 هزار";
                break;
            case "16":
                gheymat_final = "200 هزار";
                break;
            case "15":
                gheymat_final = "300 هزار";
                break;
            case "14":
                gheymat_final = "400 هزار";
                break;
            case "13":
                gheymat_final = "500 هزار";
                break;
            case "12":
                gheymat_final = "750 هزار";
                break;
            case "11":
                gheymat_final = "1 میلیون";
                break;
            case "10":
                gheymat_final = "1.5 میلیون";
                break;
            case "9":
                gheymat_final = "2 میلیون";
                break;
            case "8":
                gheymat_final = "2.5 میلیون";
                break;
            case "7":
                gheymat_final = "3 میلیون";
                break;
            case "6":
                gheymat_final = "5 میلیون";
                break;
            case "5":
                gheymat_final = "10 میلیون";
                break;
            case "4":
                gheymat_final = "20 میلیون";
                break;
            case "3":
                gheymat_final = "30 میلیون";
                break;
            case "2":
                gheymat_final = "50 میلیون";
                break;
            case "1":
                gheymat_final = "80 میلیون";
                break;
            case "0":
                gheymat_final = "100 میلیون";
                break;
        }
    }

    private String gheymatToNumber(String gheymat) {
        switch (gheymat) {
            case "27":
                gheymat_final_number = "0";
                break;
            case "26":
                gheymat_final_number = "10000";
                break;
            case "25":
                gheymat_final_number = "20000";
                break;
            case "24":
                gheymat_final_number = "30000";
                break;
            case "23":
                gheymat_final_number = "40000";
                break;
            case "22":
                gheymat_final_number = "50000";
                break;
            case "21":
                gheymat_final_number = "75000";
                break;
            case "20":
                gheymat_final_number = "100000";
                break;
            case "19":
                gheymat_final_number = "125000";
                break;
            case "18":
                gheymat_final_number = "150000";
                break;
            case "17":
                gheymat_final_number = "175000";
                break;
            case "16":
                gheymat_final_number = "200000";
                break;
            case "15":
                gheymat_final_number = "300000";
                break;
            case "14":
                gheymat_final_number = "400000";
                break;
            case "13":
                gheymat_final_number = "500000";
                break;
            case "12":
                gheymat_final_number = "750000";
                break;
            case "11":
                gheymat_final_number = "1000000";
                break;
            case "10":
                gheymat_final_number = "15000000";
                break;
            case "9":
                gheymat_final_number = "2000000";
                break;
            case "8":
                gheymat_final_number = "2500000";
                break;
            case "7":
                gheymat_final_number = "3000000";
                break;
            case "6":
                gheymat_final_number = "5000000";
                break;
            case "5":
                gheymat_final_number = "10000000";
                break;
            case "4":
                gheymat_final_number = "20000000";
                break;
            case "3":
                gheymat_final_number = "30000000";
                break;
            case "2":
                gheymat_final_number = "50000000";
                break;
            case "1":
                gheymat_final_number = "80000000";
                break;
            case "0":
                gheymat_final_number = "100000000";
                break;
        }
        return gheymat_final_number;
    }
}
