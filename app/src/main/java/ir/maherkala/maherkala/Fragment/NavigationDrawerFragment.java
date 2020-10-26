package ir.maherkala.maherkala.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ir.maherkala.maherkala.Activity.AboutDesignerActivity;
import ir.maherkala.maherkala.Activity.AboutUsActivity;
import ir.maherkala.maherkala.Activity.AdvancedSearchActivity;
import ir.maherkala.maherkala.Activity.ContactUsActivity;
import ir.maherkala.maherkala.Activity.LoginActivity;
import ir.maherkala.maherkala.Activity.MainActivity;
import ir.maherkala.maherkala.Activity.NewsActivity;
import ir.maherkala.maherkala.Activity.ProfileActivity;
import ir.maherkala.maherkala.Activity.SignupSenfActivity;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getToken;


public class NavigationDrawerFragment extends Fragment {


    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ImageView drawer_pic1, drawer_pic2, drawer_pic3, drawer_pic4, drawer_pic5, drawer_pic6, drawer_pic7, drawer_pic8, drawer_pic9;
    private TextView drawer1, drawer2, drawer3, drawer4, drawer5, drawer6, drawer7, drawer8, drawer9, loginTxt;
    private ActionBarDrawerToggle drawer_toggle;

    private boolean m_userLearnedDrawer;
    private boolean m_fromSavedInstanceState;

    private TextView BadgeCounter;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static void saveToPreferences(Context con, String preferenceName, String preferenceValue) {
        SharedPreferences sp = con.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context con, String preferenceName, String preferenceValue) {
        SharedPreferences sp = con.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        return sp.getString(preferenceName, preferenceValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_userLearnedDrawer = Boolean.valueOf(
                //inja mitan baz ya baste bodan navigation drawer dar zaman startup ra tanzim kard
                readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true")
        );

        if (savedInstanceState != null) {
            m_fromSavedInstanceState = true;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        //   drawer1 = (TextView) view.findViewById(R.id.drawer1);


        // drawer_pic1 = (ImageView) view.findViewById(R.id.drawer_pic_1);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginTxt = getView().findViewById(R.id.loginDrawer);
        BadgeCounter = getView().findViewById(R.id.badgeCounter);

        drawer1 = getView().findViewById(R.id.drawer1);
        drawer2 = getView().findViewById(R.id.drawer2);
        drawer3 = getView().findViewById(R.id.drawer3);
        drawer4 = getView().findViewById(R.id.drawer4);
        drawer5 = getView().findViewById(R.id.drawer5);
        drawer6 = getView().findViewById(R.id.drawer6);
        drawer7 = getView().findViewById(R.id.drawer7);
        drawer8 = getView().findViewById(R.id.drawer8);
        drawer9 = getView().findViewById(R.id.drawer9);


        drawer_pic1 = getView().findViewById(R.id.drawer_pic_1);
        drawer_pic2 = getView().findViewById(R.id.drawer_pic_2);
        drawer_pic3 = getView().findViewById(R.id.drawer_pic_3);
        drawer_pic4 = getView().findViewById(R.id.drawer_pic_4);
        drawer_pic5 = getView().findViewById(R.id.drawer_pic_5);
        drawer_pic6 = getView().findViewById(R.id.drawer_pic_6);
        drawer_pic7 = getView().findViewById(R.id.drawer_pic_7);
        drawer_pic8 = getView().findViewById(R.id.drawer_pic_8);
        drawer_pic9 = getView().findViewById(R.id.drawer_pic_9);

        SetupDrawer();
        Basket(getActivity());

        SharedPreferences profile = getActivity().getSharedPreferences("Profile", 0);

        getToken token = new getToken();


        //  if (!profile.getString("Fullname", "0").equals("0")) {
        if (token.Ok(getActivity())) {
            String str = profile.getString("Fullname", "0") + " خوش آمدید";
            loginTxt.setText(str);
            drawer9.setVisibility(View.VISIBLE);
            drawer_pic9.setVisibility(View.VISIBLE);


            loginTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            loginTxt.setText(getActivity().getString(R.string.SignupLogin));
            drawer9.setVisibility(View.INVISIBLE);
            drawer_pic9.setVisibility(View.INVISIBLE);

            loginTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    public void setUp(int fragmentId, DrawerLayout dl, final Toolbar toolbar) {
        View container_view = getActivity().findViewById(fragmentId);

        DrawerLayout my_drawer_layout = dl;

        drawer_toggle = new ActionBarDrawerToggle(getActivity(), dl, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!m_userLearnedDrawer) {
                    m_userLearnedDrawer = true;
                    saveToPreferences(getActivity(), PREF_FILE_NAME,
                            m_userLearnedDrawer + "");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

        };


        if (!m_userLearnedDrawer && !m_fromSavedInstanceState) {
            my_drawer_layout.openDrawer(container_view);
        }


        my_drawer_layout.setDrawerListener(drawer_toggle);

        my_drawer_layout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        drawer_toggle.setDrawerIndicatorEnabled(true);
                        drawer_toggle.syncState();
                    }
                }
        );
    }

    private void Basket(final Context context) {
        //set badgetNumber


        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder((context));
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
        getToken token = new getToken();
        if (!token.Ok(((Activity) context))) {
            BadgeCounter.setVisibility(View.GONE);
        }


    }

    public void SetupDrawer() {


        drawer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                startActivity(intent);
            }
        });
        drawer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdvancedSearchActivity.class);
                startActivity(intent);
            }
        });
        drawer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupSenfActivity.class);
                startActivity(intent);
            }
        });
        drawer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        drawer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        drawer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        drawer7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutDesignerActivity.class);
                startActivity(intent);
            }
        });
        drawer8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        drawer9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getToken getToken = new getToken();
                getToken.ClearToken(getActivity());

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
