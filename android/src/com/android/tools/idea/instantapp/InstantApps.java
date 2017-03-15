/*
 * Copyright (C) 2017 The Android Open Source Project
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
package com.android.tools.idea.instantapp;

import com.android.builder.model.AndroidAtom;
import com.android.tools.idea.gradle.project.model.AndroidModuleModel;
import com.android.tools.idea.model.MergedManifest;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.io.File;
import java.io.FileNotFoundException;

import static com.android.SdkConstants.GRADLE_PLUGIN_AIA_VERSION;
import static com.android.builder.model.AndroidProject.PROJECT_TYPE_INSTANTAPP;
import static com.intellij.openapi.util.text.StringUtil.isEmpty;

public class InstantApps {
  private static final String AIA_SDK_ENV_VAR = "WH_SDK";
  private static final String INSTANT_APP_SDK_PROPERTY = "android.instant_app_sdk_location";
  private static String AIA_SDK_LOCATION = findInstantAppSdkLocation();
  private static String AIA_PLUGIN_VERSION = GRADLE_PLUGIN_AIA_VERSION;

  @Nullable
  static String findInstantAppSdkLocation() {
    String value = System.getenv(AIA_SDK_ENV_VAR);
    if (isEmpty(value)) {
      value = System.getProperty(INSTANT_APP_SDK_PROPERTY);
    }
    return value;
  }

  @Nullable
  public static Module findInstantAppBaseSplit(@NotNull AndroidFacet facet) {
    Module baseAtomModule = null;
    AndroidModuleModel androidModuleModel = AndroidModuleModel.get(facet);
    if (facet.getProjectType() == PROJECT_TYPE_INSTANTAPP && androidModuleModel != null) {
      AndroidAtom baseSplit = androidModuleModel.getMainArtifact().getDependencies().getBaseAtom();
      if (baseSplit != null) {
        ReadAction<Module> readAction = new ReadAction<Module>() {

          @Override
          protected void run(@NotNull Result<Module> result) throws Throwable {
            result.setResult(ModuleManager.getInstance(facet.getModule().getProject()).findModuleByName(baseSplit.getAtomName()));
          }
        };
        baseAtomModule = readAction.execute().getResultObject();
      }
    }
    return baseAtomModule;
  }

  @NotNull
  public static String getDefaultInstantAppUrl(@NotNull AndroidFacet facet) {
    String defaultUrl = "<<ERROR - NO URL SET>>";
    Module baseSplit = findInstantAppBaseSplit(facet);
    if (baseSplit != null) {
      String foundUrl = new InstantAppUrlFinder(MergedManifest.get(baseSplit)).getDefaultUrl();
      defaultUrl = isEmpty(foundUrl) ? defaultUrl : foundUrl;
    }
    return defaultUrl;
  }

  @Nullable
  public static String getAiaSdkLocation() {
    return AIA_SDK_LOCATION;
  }

  @NotNull
  public static File getInstantAppSdk() throws Exception {
    String path = getAiaSdkLocation();
    if (path == null) {
      throw new Exception("Environment variable " + AIA_SDK_ENV_VAR + " not defined");
    }

    File instantAppSdkFile = new File(path);
    if (!instantAppSdkFile.exists() || !instantAppSdkFile.isDirectory()) {
      throw new FileNotFoundException(
        "Directory " + instantAppSdkFile.getAbsolutePath() + "defined in environment variable " + AIA_SDK_ENV_VAR + " does not exist");
    }

    return instantAppSdkFile;
  }

  public static boolean isInstantAppApplicationModule(@NotNull Module module) {
    AndroidModuleModel model = AndroidModuleModel.get(module);
    return model != null && model.getProjectType() == PROJECT_TYPE_INSTANTAPP;
  }

  // Can't directly set environment variables in Java so need this for testing.
  @TestOnly
  public static void setAiaSdkLocation(String value) {
    AIA_SDK_LOCATION = value;
  }

  public static String getAiaPluginVersion() {
    return AIA_PLUGIN_VERSION;
  }

  // Need to be able to override this value for testing.
  @TestOnly
  public static void setAiaPluginVersion(String value) {
    AIA_PLUGIN_VERSION = value;
  }
}
