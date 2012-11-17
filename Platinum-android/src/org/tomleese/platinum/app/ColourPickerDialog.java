package org.tomleese.platinum.app;

import org.tomleese.platinum.R;
import org.tomleese.platinum.widget.ColourHueSelector;
import org.tomleese.platinum.widget.ColourSatValSelector;
import org.tomleese.platinum.widget.ColourHueSelector.OnHueSelectedListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class ColourPickerDialog extends DialogFragment {
	
	public static interface OnColourPickedListener {
		
		public void onColour(int colour);
		public void onCancel();
		
	}
	
	private OnColourPickedListener mListener;
	private int mColour = Color.BLACK;
	private View mView;
		
	public void setOnColourPickedListener(OnColourPickedListener listener) {
		mListener = listener;
	}
	
	public void setColour(int colour) {
		mColour = colour;
		setViewColours(mColour);
	}

	private void setViewColours(int colour) {
		if (mView != null) {
			final ColourHueSelector selectorHue = (ColourHueSelector) mView
					.findViewById(R.id.selector_hue);
			final ColourSatValSelector selectorSatVal = (ColourSatValSelector) mView
					.findViewById(R.id.selector_sat_val);
			
			float hsv[] = { 0f, 1f, 1f };
			Color.RGBToHSV(Color.red(colour),
					Color.green(colour),
					Color.blue(colour), hsv);
			
			selectorHue.setHue(hsv[0]);
			selectorSatVal.setHue(hsv[0]);
			selectorSatVal.setSaturation(hsv[1]);
			selectorSatVal.setValue(hsv[2]);
			
			selectorHue.invalidate();
			selectorSatVal.invalidate();
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		mView = inflater.inflate(R.layout.dialog_colour_picker, null);
		
		final ColourHueSelector selectorHue = (ColourHueSelector) mView
				.findViewById(R.id.selector_hue);
		final ColourSatValSelector selectorSatVal = (ColourSatValSelector) mView
				.findViewById(R.id.selector_sat_val);
		
		setViewColours(mColour);
		
		selectorHue.setOnHueSelectedListener(new OnHueSelectedListener() {
			
			@Override
			public void onHue(float hue) {
				selectorSatVal.setHue(hue);
			}
			
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(mView)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int button) {
					dialog.dismiss();
					
					if (mListener != null) {
						int colour = selectorSatVal.getColour();
						mListener.onColour(colour);
					}
				}
				
			})
			.setNegativeButton(android.R.string.cancel, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					
					if (mListener != null) {
						mListener.onCancel();
					}
				}
			})
			.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
					
					if (mListener != null) {
						mListener.onCancel();
					}
				}
				
			})
			.create();
	}

}
