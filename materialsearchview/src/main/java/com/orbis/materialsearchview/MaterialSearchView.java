package com.orbis.materialsearchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MaterialSearchView extends FrameLayout implements View.OnClickListener {

    private EditText txtSearch;
    private ImageButton btnBack;
    private ImageButton btnClear;

    private LinearLayout lnlContainer;
    private LinearLayout lnlSearch;
    private CardView cvSearch;


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
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.filter_layout, this, true);

        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "22", Toast.LENGTH_SHORT).show();
            }
        });

        txtSearch = (EditText) findViewById(R.id.txtSearch);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnClear = (ImageButton) findViewById(R.id.btnClear);
        lnlContainer = (LinearLayout) findViewById(R.id.lnlContainer);
        lnlSearch = (LinearLayout) findViewById(R.id.lnlSearch);
        cvSearch = (CardView) findViewById(R.id.cvSearch);

        setVisibility(INVISIBLE);

        btnBack.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        lnlSearch.setOnClickListener(this);
        lnlContainer.setOnClickListener(this);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MaterialSearchView, defStyleAttr, defStyleRes);

        a.recycle();
    }

    public void closeSearch() {
        setVisibility(GONE);
    }


    public void setVisibleWithAnimation() {
        AnimationUtil.AnimationListener animationListener = new AnimationUtil.AnimationListener() {
            @Override
            public boolean onAnimationStart(View view) {
                return false;
            }

            @Override
            public boolean onAnimationEnd(View view) {
                Toast.makeText(getContext(), "ANIMATION END", Toast.LENGTH_SHORT).show();

                lnlContainer.setBackgroundResource(R.color.md_black_1000_50);
                return false;
            }

            @Override
            public boolean onAnimationCancel(View view) {
                return false;
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setVisibility(View.VISIBLE);
            AnimationUtil.reveal(cvSearch, animationListener);

        } else {
            // TODO da sistemare
            AnimationUtil.fadeInView(this, 3000, animationListener);
        }
    }

    @Override
    public void onClick(View view) {
        closeSearch();
    }

}
