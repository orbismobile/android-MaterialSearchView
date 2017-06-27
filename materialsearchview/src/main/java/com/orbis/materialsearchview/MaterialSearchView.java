package com.orbis.materialsearchview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MaterialSearchView extends FrameLayout implements View.OnClickListener {

    private EditText txtSearch;
    private ImageButton btnBack;
    private ImageButton btnClear;

    private FrameLayout flGeneral;
    private LinearLayout lnlSearch;
    private CardView cvSearch;
    private View vShadow;

    private RecyclerView rcvSearch;
    private List<Object> objectList = new ArrayList<>();
    private SearchAdapter searchAdapter;

    //Animator for reveal animation
    private Animator anim;

    //Animation for vShadow
    private Animation animationFadeInShadow;

    private Animation animationFadeInView;


    public MaterialSearchView(Context context) {
        this(context, null);
    }

    public MaterialSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initStyle(attrs, 0, 0);
    }

    public MaterialSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(attrs, defStyleAttr, 0);
        initView();
    }

    private void initView() {
        //set invisible when the widget is created
        setVisibility(INVISIBLE);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.search_layout, this, true);

        cvSearch = (CardView) view.findViewById(R.id.cvSearch);
        lnlSearch = (LinearLayout) view.findViewById(R.id.lnlSearch);
        rcvSearch = (RecyclerView) view.findViewById(R.id.rcvSearch);
        btnBack = (ImageButton) view.findViewById(R.id.btnBack);
        txtSearch = (EditText) view.findViewById(R.id.txtSearch);
        btnClear = (ImageButton) view.findViewById(R.id.btnClear);
        vShadow = view.findViewById(R.id.vShadow);

        vShadow.setOnClickListener(this);
        lnlSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        //SetUp Animation for vShadow
        animationFadeInShadow = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_shadow);

        animationFadeInView = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_view);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MaterialSearchView, defStyleAttr, defStyleRes);

        a.recycle();
    }

    public void initFirstSetup(List<Object> objects, SearchAdapter searchAdapter) {
        this.objectList = objects;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSearch.setLayoutManager(linearLayoutManager);
        this.searchAdapter = searchAdapter;
        //this.searchAdapter.addDataList(objectList);
        rcvSearch.setAdapter(this.searchAdapter);
    }

    public void setVisibleWithAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(1, false, true);
        } else {
            // TODO all systems
            fadeInMaterialSearchView(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnClear) {
            Toast.makeText(getContext(), "AA", Toast.LENGTH_SHORT).show();
        } else { //vShadow && btnBack
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(1, false, false);
            } else {
                fadeInMaterialSearchView(false);
            }
        }
    }

    public int getCurrentVisibility() {
        return getVisibility();
    }


    //This method show or hide the secondToolbar with the Reveal Animation
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int menuItemPositionFromRight, boolean containsOverflow, final boolean shouldShowSecondToolbar) {

        // make the view visible and start the animation
        if (shouldShowSecondToolbar) {
            setVisibility(View.VISIBLE);
        } else {
            objectList.clear();
            searchAdapter.notifyDataSetChanged();
        }

        int width = lnlSearch.getWidth();

        // getDimensionPixelOffset() in my case 48dp returns 96 pixels
        if (menuItemPositionFromRight > 0)
            //we subtract menuItem size from the right
            width -= (menuItemPositionFromRight * getResources().getDimensionPixelOffset(R.dimen.action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.action_button_min_width_material) / 2);

        //if we are in a toolbar we should subtract a overflowSize
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.action_button_min_width_overflow_material);

        int cx = width;
        int cy = lnlSearch.getHeight() / 2;

        if (anim != null && anim.isRunning()) {
            return;
        }

        if (shouldShowSecondToolbar)
            anim = ViewAnimationUtils.createCircularReveal(cvSearch, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(cvSearch, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (shouldShowSecondToolbar) {
                    vShadow.setAnimation(animationFadeInShadow);
                    vShadow.setVisibility(VISIBLE);
                    searchAdapter.notifyDataSetChanged();
                } else {
                    setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.start();
    }


    public void fadeInMaterialSearchView(final boolean shouldShowSecondToolbar) {

        // make the view visible and start the animation
        if (shouldShowSecondToolbar) {
            setVisibility(View.VISIBLE);
        } else {
            objectList.clear();
            searchAdapter.notifyDataSetChanged();
        }


        if (shouldShowSecondToolbar)
            this.setAnimation(animationFadeInView);
        else
            setVisibility(INVISIBLE);

        animationFadeInView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getContext(), "notify " + shouldShowSecondToolbar, Toast.LENGTH_SHORT).show();
                if (shouldShowSecondToolbar) {

                    vShadow.setAnimation(animationFadeInShadow);
                    vShadow.setVisibility(VISIBLE);
                    searchAdapter.notifyDataSetChanged();
                } else {
                    setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationFadeInView.start();
    }
}
