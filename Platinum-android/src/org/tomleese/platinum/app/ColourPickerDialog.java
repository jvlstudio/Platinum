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

/**
 * A dialog for showing a colour picker. This uses the ColourHueSelector and
 * ColourSatValSelector widgets.
 * 
 * This dialog is implemented as a DialogFragment from the support library.
 * 
 * @author Tom Leese
 */
public class ColourPickerDialog extends DialogFragment {

	/**
	 * The interface callback used when a colour is picked.
	 * 
	 * @author Tom Leese
	 */
	public static interface OnColourPickedListener {

		/**
		 * Called when a colour is picked.
		 * 
		 * @param colour The picked colour
		 */
		public void onColour(int colour);
		
		/**
		 * Called when the dialog is cancelled.
		 */
		public void onCancel();

	}

	private OnColourPickedListener mListener;
	private int mColour = Color.BLACK;
	private View mView;

	/**
	 * Sets the picked listener for this dialog. 
	 * 
	 * @param listener The OnColourPickedListener to be used in this dialog
	 */
	public void setOnColourPickedListener(OnColourPickedListener listener) {
		mListener = listener;
	}

	/**
	 * Sets the current colour of the dialog.
	 * 
	 * @param colour A colour
	 */
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
			Color.RGBToHSV(Color.red(colour), Color.green(colour),
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
				.setNegativeButton(android.R.string.cancel,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

								if (mListener != null) {
									mListener.onCancel();
								}
							}
						}).setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						dialog.dismiss();

						if (mListener != null) {
							mListener.onCancel();
						}
					}

				}).create();
	}

}
