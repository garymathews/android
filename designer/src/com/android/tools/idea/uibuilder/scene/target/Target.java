/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.tools.idea.uibuilder.scene.target;

import com.android.tools.idea.uibuilder.model.AndroidDpCoordinate;
import com.android.tools.idea.uibuilder.scene.SceneComponent;
import com.android.tools.idea.uibuilder.scene.ScenePicker;
import com.android.tools.idea.uibuilder.scene.SceneContext;
import com.android.tools.idea.uibuilder.scene.draw.DisplayList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

/**
 * {@link Target} are associated with s {@link SceneComponent} and are used to handle
 * user interactions.
 *
 * A {@link Target} can be used to receive mouse event and can be detected by the {@link ScenePicker} to be passed
 * in mouse event as closest target. If two target overlaps, the {@link #getPreferenceLevel()} will be used to select the one that will be selected.
 *
 * A Target
 */
public interface Target {
  // List of preference levels
  int LASSO_LEVEL = 10;
  int DRAG_LEVEL = 20;
  int GUIDELINE_ANCHOR_LEVEL = 30;
  int GUIDELINE_LEVEL = 40;
  int SIDE_RESIZE_LEVEL = 50;
  int RESIZE_LEVEL = 60;
  int ANCHOR_LEVEL = 70;
  int ACTION_LEVEL = 80;

  /**
   * Set priority of the {@link Target} so the {@link com.android.tools.idea.uibuilder.scene.Scene} knows which one to use.
   * The higher value is, the higher the priority will be
   *
   * @return the preference level
   */
  int getPreferenceLevel();

  /**
   * Implementing classes should make their measurement using this method
   *
   * @param context Current {@link SceneContext}
   * @param l       left - Current left coordinate of the parent
   * @param t       top - Current top coordinate of the parent
   * @param r       right - Current right coordinate of the parent
   * @param b       bottom - Current bottom coordinate of the parent
   * @return true if the {@link com.android.tools.idea.uibuilder.scene.Scene} needs to be repainted.
   */
  boolean layout(@NotNull SceneContext context,
                 @AndroidDpCoordinate int l,
                 @AndroidDpCoordinate int t,
                 @AndroidDpCoordinate int r,
                 @AndroidDpCoordinate int b);

  /**
   * Implementing classes should use this method to add a hit region to the provided {@link ScenePicker} that can handle the interaction.
   *
   * @param context The {@link SceneContext} used to transform coodinates when adding a region to the {@link ScenePicker}
   * @param picker  The picker to add the hii region too
   */
  void addHit(@NotNull SceneContext context, @NotNull ScenePicker picker);

  void setComponent(@NotNull SceneComponent component);

  /**
   * Implementing classes should add their own {@link com.android.tools.idea.uibuilder.scene.draw.DrawCommand} to the provided
   * {@link DisplayList} to draw any needed graphic
   *
   * @param list         The list where the {@link com.android.tools.idea.uibuilder.scene.draw.DrawCommand}
   *                     will be added
   * @param sceneContext The current {@link SceneContext} used to compute transform for
   *                     the {@link com.android.tools.idea.uibuilder.scene.draw.DrawCommand}
   */
  void render(@NotNull DisplayList list, @NotNull SceneContext sceneContext);

  void setOver(boolean over);

  /**
   * Can be used by the target to expand its bounds
   *
   * @param expand true to expand
   */
  void setExpandSize(boolean expand);

  String getToolTipText();

  /**
   * Implement this method to react to mouse down events
   *
   * @param x X coordinate of the mouse in DP
   * @param y Y coordinate of the mouse in DP
   */
  default void mouseDown(@AndroidDpCoordinate int x, @AndroidDpCoordinate int y) {
  }

  /**
   * Implement this method to react to mouse drag events
   *
   * @param x             X coordinate of the mouse in DP
   * @param y             Y coordinate of the mouse in DP
   * @param closestTarget Closest target from the mouse if there is one
   */
  default void mouseDrag(@AndroidDpCoordinate int x, @AndroidDpCoordinate int y, @Nullable List<Target> closestTargets) {
  }

  /**
   * Implement this method to react to mouse release events
   *
   * @param x             X coordinate of the mouse in DP
   * @param y             Y coordinate of the mouse in DP
   * @param closestTarget Closest target from the mouse if there is one
   */
  default void mouseRelease(@AndroidDpCoordinate int x, @AndroidDpCoordinate int y, @Nullable List<Target> closestTargets) {
  }

  SceneComponent getComponent();

  Cursor getMouseCursor();

  @AndroidDpCoordinate
  float getCenterX();

  @AndroidDpCoordinate
  float getCenterY();

  boolean canChangeSelection();

  /**
   * Notified when the underlying component changes selection
   *
   * @param selection
   */
  void setComponentSelection(boolean selection);
}
