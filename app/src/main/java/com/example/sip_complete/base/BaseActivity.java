package com.example.sip_complete.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sip_complete.R;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Handler handler;
    protected ProgressDialog mSimpleProgress;

    private boolean mHaveDisplayCutout = false;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        mContext = this;
        handler = new Handler();
        initView();
        eventHandle();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void eventHandle();

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getActionBarView() != null)
            getActionBarView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        return super.onCreateOptionsMenu(menu);
    }

    public void setActionBarVisible(boolean isVisible) {
        View actionBarView = getActionBarView();
        if (actionBarView != null) {
            getActionBarView().setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    public View getActionBarView() {
        Window window = getWindow();
        View decorView = window.getDecorView();
        int resId;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            resId = getResources().getIdentifier("action_bar_container", "id", getPackageName());
        } else {
            resId = Resources.getSystem().getIdentifier("action_bar_container", "id", "android");
        }
        if (resId != 0 && decorView.findViewById(resId) != null) {
            return decorView.findViewById(resId);
        } else {
            final String packageName = this instanceof AppCompatActivity ? this.getPackageName() : "android";
            resId = this.getResources().getIdentifier("action_bar_container", "id", packageName);

            if (resId != 0 && decorView.findViewById(resId) != null)
                return decorView.findViewById(resId);
        }
        return null;
    }

    public void showSimpleProgress() {
        // Simple progress bar
        if (mSimpleProgress != null) {
            mSimpleProgress.dismiss();
        }
        mSimpleProgress = new ProgressDialog(BaseActivity.this, R.style.SplashTheme);
        mSimpleProgress.setCancelable(false);
        mSimpleProgress.show();
        mSimpleProgress.setContentView(R.layout.progress_simple);
    }

    public void hideSimpleProgress() {
        if (mSimpleProgress != null) {
            mSimpleProgress.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public boolean isHaveDisplayCutout() {
        return mHaveDisplayCutout;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean isInMultiWindowMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            return super.isInMultiWindowMode();
        return false;
    }

    protected int getFragmentContentId() {
        return R.id.tab_content;
    }

    public synchronized void pushFragment(Fragment fragment, boolean shouldAnimate, boolean isAddToStack, boolean removeLastInstance) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//        if (shouldAnimate)
//            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
//        ft.replace(getFragmentContentId(), fragment);
//        if (isAddToStack) {
//            ft.addToBackStack(null);
//        }
//        ft.commit();
        pushFragment(fragment, shouldAnimate, isAddToStack, removeLastInstance, null);
    }

    /**
     * Push Fragment
     */
    public synchronized void pushFragment(Fragment fragment, boolean shouldAnimate, boolean isAddToStack, boolean removeLastInstance, String backStackName) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
//        if (shouldAnimate)
//            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(getFragmentContentId(), fragment);
        if (isAddToStack) {
            ft.addToBackStack(backStackName);
        }
        ft.commit();
    }

    public synchronized void popFragment(boolean shouldAnimate) {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();
    }

    public synchronized void popBackStack(String backStackName) {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack(backStackName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * @return true if this activity is second Activity in dual screen mode
     */
    public boolean isOpenInDualScreen() {
        return false;
    }

    /**
     * Remove all fragment in this activity
     */
    public void removeAllFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Remove full screen dialof if running
        Fragment fullScreenDialogFragment = fragmentManager.findFragmentByTag("full_player");
        try {
            if (fullScreenDialogFragment != null) {
                if (fullScreenDialogFragment instanceof DialogFragment) {
                    ((DialogFragment) fullScreenDialogFragment).dismiss();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                }
                fragmentManager.beginTransaction().remove(fullScreenDialogFragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        removeFragFromBackStack();
    }

    /**
     * Remove all fragment in backstack
     */
    private boolean removeFragFromBackStack() {
        try {
            FragmentManager manager = getSupportFragmentManager();
            List<Fragment> fragsList = manager.getFragments();
            if (fragsList.size() == 0) {
                return true;
            }
            manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showToast(Object message) {
        if (message instanceof Integer) {
            int result = (Integer) message;
            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        } else if (message instanceof String) {
            String result = (String) message;
            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        }
    }
}