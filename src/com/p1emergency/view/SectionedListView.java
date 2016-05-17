package com.p1emergency.view;

import com.p1emergency.adapter.SeparatedListAdapter;

import com.p1emergency.root.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * Custom list view
 * 
 */
public class SectionedListView extends ListView implements OnScrollListener {

	private OnScrollListener scrollListener;
	private boolean areHeadersSticky;
	private int headerBottomPosition;
	private int headerHeight = -1;
	private View header;
	private boolean headerHasChanged = true;
	private boolean setupDone;
	private View lastWatchedViewHeader;
	private Rect clippingRect = new Rect();
	private int mSectionNum;
	private int mNotHeaderValue = 1000000;

	public SectionedListView(Context context) {
		super(context);
	}

	public SectionedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.StickyListHeadersListView);
		setAreHeadersSticky(a.getBoolean(0, true));
		a.recycle();
		setup();
	}

	public SectionedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.StickyListHeadersListView);
		setAreHeadersSticky(a.getBoolean(0, true));
		a.recycle();
		setup();
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
	}

	public void setAreHeadersSticky(boolean areHeadersSticky) {
		if (areHeadersSticky) {
			super.setVerticalFadingEdgeEnabled(false);
		}
		this.areHeadersSticky = areHeadersSticky;
	}

	private void setup() {
		if (!setupDone) {
			setupDone = true;
			super.setOnScrollListener(this);
			setVerticalFadingEdgeEnabled(false);
		}
	}

	@Override
	public void setVerticalFadingEdgeEnabled(boolean verticalFadingEdgeEnabled) {
		if (areHeadersSticky) {
			super.setVerticalFadingEdgeEnabled(false);
		} else {
			super.setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled);
		}
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		scrollListener = l;
	}

	public boolean areHeadersSticky() {
		return areHeadersSticky;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (header != null && areHeadersSticky) {
			if (headerHasChanged) {
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth(),
						MeasureSpec.AT_MOST);
				int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
				header.measure(widthMeasureSpec, heightMeasureSpec);
				header.layout(getLeft() + getPaddingLeft(), 0, getRight()
						- getPaddingRight(), headerHeight);
				headerHasChanged = false;
			}
			// Calculating header rectangle size
			int top = headerBottomPosition - headerHeight;
			clippingRect.left = getPaddingLeft();
			clippingRect.right = getWidth() - getPaddingRight();
			clippingRect.bottom = top + headerHeight;
			clippingRect.top = 0;
			canvas.save();
			// Cliping canvas to rectangle size
			canvas.clipRect(clippingRect);
			// To get the translate effect visible when one header push previous
			// header
			canvas.translate(getPaddingTop(), top);
			// Draw header
			header.draw(canvas);
			canvas.restore();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (scrollListener != null) {
			scrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
		if (getAdapter() == null)
			return;
		if (areHeadersSticky) {
			if (getChildCount() != 0) {

				View viewToWatch = getChildAt(0);
				for (int i = 1; i < getChildCount(); i++) {
					int firstChildDistance;
					firstChildDistance = Math.abs(viewToWatch.getTop());
					int secondChildDistance;
					secondChildDistance = Math.abs(getChildAt(i).getTop()
							- headerHeight);

					// Get view other then top
					if (!(viewToWatch.getId() == R.id.header_view)
							|| ((viewToWatch.getId() == R.id.header_view) && secondChildDistance < firstChildDistance)) {
						viewToWatch = getChildAt(i);
					}
				}
				// Calculate header bottom position to so that
				// rectangle(clippingRect) size can be determined and one header
				// can translate other
				if (viewToWatch.getId() == R.id.header_view) {
					if (headerHeight < 0)
						headerHeight = viewToWatch.findViewById(
								R.id.header_view).getHeight();

					if (firstVisibleItem == 0 && getChildAt(0).getTop() > 0) {
						headerBottomPosition = 0;
					} else {

						headerBottomPosition = Math.min(viewToWatch.getTop(),
								headerHeight);
						headerBottomPosition = headerBottomPosition < 0 ? headerHeight
								: headerBottomPosition;

					}
					lastWatchedViewHeader = viewToWatch
							.findViewById(R.id.header_view);
					if ((headerBottomPosition == headerHeight)
							&& viewToWatch.getTop() < headerHeight) {
						lastWatchedViewHeader.setVisibility(View.INVISIBLE);
					} else {
						lastWatchedViewHeader.setVisibility(View.VISIBLE);
					}
				} else {
					headerBottomPosition = headerHeight;

				}
			}
			// When first visible item in list is header translate the header
			// view
			if ((mSectionNum = ((SeparatedListAdapter) getAdapter())
					.getSectionNumber(firstVisibleItem)) != mNotHeaderValue) {
				headerHasChanged = true;
				header = ((SeparatedListAdapter) getAdapter()).getHeaderView(
						mSectionNum, header);
				header.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT, headerHeight));

			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollListener != null) {
			scrollListener.onScrollStateChanged(view, scrollState);
		}
	}
}
