/*
 * Copyright (C) 2015 The Android Open Source Project
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
 *
 * THIS FILE WAS GENERATED BY codergen. EDIT WITH CARE.
 */
package com.android.tools.idea.editors.gfxtrace.service.gfxapi;

import com.android.tools.rpclib.binary.Decoder;
import com.android.tools.rpclib.binary.Encoder;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class ResourceType {
  public static final ResourceType TypeUnknown = new ResourceType(0, "TypeUnknown");
  public static final int TypeUnknownValue = 0;
  public static final ResourceType TypeTexture1D = new ResourceType(1, "TypeTexture1D");
  public static final int TypeTexture1DValue = 1;
  public static final ResourceType TypeTexture2D = new ResourceType(2, "TypeTexture2D");
  public static final int TypeTexture2DValue = 2;
  public static final ResourceType TypeTexture3D = new ResourceType(3, "TypeTexture3D");
  public static final int TypeTexture3DValue = 3;
  public static final ResourceType TypeCubemap = new ResourceType(4, "TypeCubemap");
  public static final int TypeCubemapValue = 4;

  private static final ImmutableMap<Integer, ResourceType> VALUES = ImmutableMap.<Integer, ResourceType>builder()
    .put(0, TypeUnknown)
    .put(1, TypeTexture1D)
    .put(2, TypeTexture2D)
    .put(3, TypeTexture3D)
    .put(4, TypeCubemap)
    .build();

  private final int myValue;
  private final String myName;

  private ResourceType(int v, String n) {
    myValue = v;
    myName = n;
  }

  public int getValue() {
    return myValue;
  }

  public String getName() {
    return myName;
  }

  public void encode(@NotNull Encoder e) throws IOException {
    e.int32(myValue);
  }

  public static ResourceType decode(@NotNull Decoder d) throws IOException {
    return findOrCreate(d.int32());
  }

  public static ResourceType find(int value) {
    return VALUES.get(value);
  }

  public static ResourceType findOrCreate(int value) {
    ResourceType result = VALUES.get(value);
    return (result == null) ? new ResourceType(value, null) : result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || !(o instanceof ResourceType)) return false;
    return myValue == ((ResourceType)o).myValue;
  }

  @Override
  public int hashCode() {
    return myValue;
  }

  @Override
  public String toString() {
    return (myName == null) ? "ResourceType(" + myValue + ")" : myName;
  }
}