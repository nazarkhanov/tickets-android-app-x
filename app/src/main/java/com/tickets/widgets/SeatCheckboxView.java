package com.tickets.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.tickets.R;

public class SeatCheckboxView extends AppCompatCheckBox {

    static private final int OCCUPIED = -1;

    static private final int AVAILABLE = 0;

    static private final int SELECTED = 1;

    private int state = AVAILABLE;
    private boolean isScaled = false;

    private final OnCheckedChangeListener privateListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (state) {
                case AVAILABLE:
                    setState(SELECTED);
                    break;
                case SELECTED:
                    setState(AVAILABLE);
                    break;
            }
        }
    };

    private OnCheckedChangeListener clientListener;
    private boolean restoring;

    public SeatCheckboxView(Context context) {
        super(context);
        init();
    }

    public SeatCheckboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SeatCheckboxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if(!this.restoring && this.state != state) {
            this.state = state;

            if(this.clientListener != null) {
                this.clientListener.onCheckedChanged(this, this.isChecked());
            }

            updateBtn();
        }
    }

    @Override
    public void setOnCheckedChangeListener(@Nullable OnCheckedChangeListener listener) {
        if(this.privateListener != listener) {
            this.clientListener = listener;
        }

        super.setOnCheckedChangeListener(privateListener);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);

        ss.state = state;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        this.restoring = true;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setState(ss.state);
        requestLayout();
        this.restoring = false;
    }

    private void init() {
        updateBtn();
        setOnCheckedChangeListener(this.privateListener);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeatCheckboxView);
        state = typedArray.getInt(R.styleable.SeatCheckboxView_state, 0);
        isScaled = typedArray.getBoolean(R.styleable.SeatCheckboxView_scale, false);
        typedArray.recycle();
        init();
    }

    private void updateBtn() {
        int btnDrawable = isScaled ?R.drawable.ic_seat_occupied_2x : R.drawable.ic_seat_occupied;
        switch (state) {
            case OCCUPIED:
                btnDrawable = isScaled ?R.drawable.ic_seat_occupied_2x : R.drawable.ic_seat_occupied;
                break;
            case AVAILABLE:
                btnDrawable = isScaled ?R.drawable.ic_seat_available_2x : R.drawable.ic_seat_available;
                break;
            case SELECTED:
                btnDrawable = isScaled ?R.drawable.ic_seat_selected_2x : R.drawable.ic_seat_selected;
                break;
        }
        setButtonDrawable(btnDrawable);
    }

    static class SavedState extends BaseSavedState {
        int state;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            state = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(state);
        }

        @Override
        public String toString() {
            return "SeatCheckboxView.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " state=" + state + "}";
        }

        @SuppressWarnings("hiding")
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}