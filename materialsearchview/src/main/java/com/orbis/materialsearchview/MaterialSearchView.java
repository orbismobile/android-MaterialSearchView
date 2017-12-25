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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MaterialSearchView extends FrameLayout implements View.OnClickListener {

    //Views
    private LinearLayout lnlSearch;
    private EditText etSearch;
    private ImageButton btnBack;
    private ImageButton btnClear;

    private CardView cvSearch;
    private View vShadow;
    private RecyclerView rcvSearch;

    //Animator for reveal animation
    private Animator anim;

    //Animation for vShadow
    private Animation animationFadeInShadow;
    private Animation animationFadeInView;

    //LIstener
    private OnQueryTextListener mOnQueryChangeListener;





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
        cvSearch = view.findViewById(R.id.cvSearch);
        rcvSearch = view.findViewById(R.id.rcvSearch);

        lnlSearch = (LinearLayout) view.findViewById(R.id.lnlSearch);
        btnBack = (ImageButton) view.findViewById(R.id.btnBack);
        etSearch = (EditText) view.findViewById(R.id.etSearch);
        btnClear = (ImageButton) view.findViewById(R.id.btnClear);

        vShadow = view.findViewById(R.id.vShadow);

        //Listener for SearchView
        vShadow.setOnClickListener(this);
        lnlSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        //SetUp Animation for vShadow
        animationFadeInShadow = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_shadow);
        animationFadeInView = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_view);


        etSearch.setOnEditorActionListener(mOnEditorActionListener);

    }

    private void initStyle(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MaterialSearchView, defStyleAttr, defStyleRes);

        a.recycle();


    }







    private final TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {

        /**
         * Called when the input method default action key is pressed.
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            onSubmitQuery();
            return true;
        }
    };


    void onSubmitQuery() {
        CharSequence query = etSearch.getText();
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            if (mOnQueryChangeListener == null
                    || !mOnQueryChangeListener.onQueryTextSubmit(query.toString())) {
                //TODO
            }
        }
    }






    /**
     * Callbacks for changes to the query text.
     */
    public interface OnQueryTextListener {

        /**
         * Called when the user submits the query. This could be due to a key press on the
         * keyboard or due to pressing a submit button.
         * The listener can override the standard behavior by returning true
         * to indicate that it has handled the submit request. Otherwise return false to
         * let the SearchView handle the submission by launching any associated intent.
         *
         * @param query the query text that is to be submitted
         * @return true if the query has been handled by the listener, false to let the
         * SearchView perform the default action.
         */
        boolean onQueryTextSubmit(String query);

        /**
         * Called when the query text is changed by the user.
         *
         * @param newText the new content of the query text field.
         * @return false if the SearchView should perform the default action of showing any
         * suggestions if available, true if the action was handled by the listener.
         */
        boolean onQueryTextChange(String newText);

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
     *
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

}
