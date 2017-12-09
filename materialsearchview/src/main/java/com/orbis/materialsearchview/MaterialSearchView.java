package com.orbis.materialsearchview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MaterialSearchView extends FrameLayout implements View.OnClickListener, SearchView.OnFocusChangeListener {

    //Views
    public SearchView svSearch;
    private CardView cvSearch;
    private View vShadow;
    private RecyclerView rcvSearch;

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
        rcvSearch = (RecyclerView) view.findViewById(R.id.rcvSearch);
        svSearch = (SearchView) view.findViewById(R.id.svSearch);
        vShadow = view.findViewById(R.id.vShadow);

        //Listener for SearchView
        vShadow.setOnClickListener(this);
        svSearch.setOnClickListener(this);
        svSearch.setOnQueryTextFocusChangeListener(this);

        //Setting up SearchView Properties
        LinearLayout linearLayout = (LinearLayout) svSearch.findViewById(R.id.search_edit_frame);
        ((LinearLayout.LayoutParams) linearLayout.getLayoutParams()).leftMargin = 0;

        //SetUp Animation for vShadow
        animationFadeInShadow = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_shadow);
        animationFadeInView = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_view);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MaterialSearchView, defStyleAttr, defStyleRes);

        a.recycle();
    }

    /**
     * This method set up the recycler view with the adapter
     *
     * @param searchAdapter The adapter to manage the suggestions
     */
    public void initFirstSetup(SearchAdapter searchAdapter) {
        rcvSearch.setAdapter(searchAdapter);
    }

    /**
     * This method start the animation of the searcher,
     * Additionally support the animation for version higher 25
     */
    public void startSearcherAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(1, false, true);
        } else {
            fadeInMaterialSearchView(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.svSearch || view.getId() == R.id.vShadow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(1, false, false);
            } else {
                fadeInMaterialSearchView(false);
            }
        }
    }

    /**
     * This method show or hide the secondToolbar with the Reveal Animation
     *
     * @param menuItemPositionFromRight The position where the animation start
     * @param containsOverflow          A boolean value for subtract additional space in a toolbar
     * @param showSearcher              A boolean value if should show or hide the searcher
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int menuItemPositionFromRight, boolean containsOverflow, final boolean showSearcher) {

        // make the view visible and start the animation
        if (showSearcher) setVisibility(View.VISIBLE);

        int width = svSearch.getWidth();

        // getDimensionPixelOffset() in my case 48dp returns 96 pixels
        if (menuItemPositionFromRight > 0)
            //we subtract menuItem size from the right
            width -= (menuItemPositionFromRight * getResources().getDimensionPixelOffset(R.dimen.action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.action_button_min_width_material) / 2);

        //if we are in a toolbar we should subtract a overflowSize
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.action_button_min_width_overflow_material);

        int cx = width;
        int cy = svSearch.getHeight() / 2;

        if (anim != null && anim.isRunning()) {
            return;
        }

        rcvSearch.setVisibility(GONE);

        if (showSearcher) {
            anim = ViewAnimationUtils.createCircularReveal(cvSearch, cx, cy, 0, (float) width);
        } else {
            anim = ViewAnimationUtils.createCircularReveal(cvSearch, cx, cy, (float) width, 0);
        }

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (showSearcher) {
                    vShadow.setAnimation(animationFadeInShadow);
                    vShadow.setVisibility(VISIBLE);

                    rcvSearch.setVisibility(VISIBLE);

                    showInputMethod(svSearch.findFocus());
                } else {
                    setVisibility(View.INVISIBLE);

                }
            }
        });
        anim.start();
    }

    /**
     * This method show or hide the secondToolbar with the FadeIn o Out Animation
     * Support lower than 21
     * @param showSearcher A boolean value if should show or hide the searcher
     */
    public void fadeInMaterialSearchView(final boolean showSearcher) {
        // make the view visible and start the animation
        if (showSearcher) setVisibility(View.VISIBLE);

        if (showSearcher)
            this.setAnimation(animationFadeInView);
        else
            setVisibility(INVISIBLE);

        rcvSearch.setVisibility(GONE);

        animationFadeInView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (showSearcher) {
                    vShadow.setAnimation(animationFadeInShadow);
                    vShadow.setVisibility(VISIBLE);

                    rcvSearch.setVisibility(VISIBLE);
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(1, false, false);
            } else {
                fadeInMaterialSearchView(false);
            }
        } else {
            showInputMethod(v.findFocus());
        }
    }

    public void showInputMethod(View view) {
        if (svSearch.hasFocus()) {
            InputMethodManager inputManager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, 0);
        }
    }
}
