package com.tickets.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tickets.R;
import com.tickets.databinding.ItemMenuBinding;

public class MenuItemView extends ConstraintLayout {

    private ItemMenuBinding binding;
    private OnClickListener listener;

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        binding = ItemMenuBinding.inflate(LayoutInflater.from(context), this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MenuItemView,
                0, 0);

        try {
            String text = a.getString(R.styleable.MenuItemView_text);
            int icon = a.getResourceId(0, R.styleable.MenuItemView_icon);

            setText(text);
            setIcon(icon);
        } finally {
            a.recycle();
        }
    }

    public void setText(String text) {
        binding.text.setText(text);
    }

    public void setIcon(int resId) {
        binding.icon.setImageResource(resId);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP && listener != null) {
            listener.onClick(binding.getRoot());
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        this.listener = l;
    }
}