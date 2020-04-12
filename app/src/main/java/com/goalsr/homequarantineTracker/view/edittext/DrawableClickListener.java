package com.goalsr.homequarantineTracker.view.edittext;

/**
 * Created by RTLPC3 on 5/11/2019.
 */

 public interface DrawableClickListener {

    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);
}
