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
package com.android.tools.idea.editors.gfxtrace.service;

import com.android.tools.rpclib.binary.BinaryID;
import com.android.tools.idea.editors.gfxtrace.service.path.Path;
import com.android.tools.rpclib.any.Box;
import com.android.tools.idea.editors.gfxtrace.service.path.CapturePath;
import com.android.tools.idea.editors.gfxtrace.service.path.DevicePath;
import com.android.tools.idea.editors.gfxtrace.service.path.ImageInfoPath;
import com.android.tools.idea.editors.gfxtrace.service.path.AtomPath;
import com.android.tools.idea.editors.gfxtrace.service.path.TimingInfoPath;
import com.android.tools.rpclib.rpccore.Broadcaster;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListenableFuture;

public final class ServiceClientRPC extends ServiceClient {
  private final Broadcaster myBroadcaster;
  private final ListeningExecutorService myExecutorService;

  public ServiceClientRPC(ListeningExecutorService executorService, InputStream in, OutputStream out, int mtu) {
    myExecutorService = executorService;
    myBroadcaster = new Broadcaster(in, out, mtu, myExecutorService);
  }
  @Override
  public ListenableFuture<Path> follow(Path p) {
    return myExecutorService.submit(new FollowCallable(p));
  }
  @Override
  public ListenableFuture<Object> get(Path p) {
    return myExecutorService.submit(new GetCallable(p));
  }
  @Override
  public ListenableFuture<CapturePath[]> getCaptures() {
    return myExecutorService.submit(new GetCapturesCallable());
  }
  @Override
  public ListenableFuture<DevicePath[]> getDevices() {
    return myExecutorService.submit(new GetDevicesCallable());
  }
  @Override
  public ListenableFuture<ImageInfoPath> getFramebufferColor(DevicePath device, AtomPath after, RenderSettings settings) {
    return myExecutorService.submit(new GetFramebufferColorCallable(device, after, settings));
  }
  @Override
  public ListenableFuture<ImageInfoPath> getFramebufferDepth(DevicePath device, AtomPath after) {
    return myExecutorService.submit(new GetFramebufferDepthCallable(device, after));
  }
  @Override
  public ListenableFuture<Schema> getSchema() {
    return myExecutorService.submit(new GetSchemaCallable());
  }
  @Override
  public ListenableFuture<TimingInfoPath> getTimingInfo(DevicePath device, CapturePath capture, TimingFlags flags) {
    return myExecutorService.submit(new GetTimingInfoCallable(device, capture, flags));
  }
  @Override
  public ListenableFuture<CapturePath> importCapture(String name, byte[] Data) {
    return myExecutorService.submit(new ImportCaptureCallable(name, Data));
  }
  @Override
  public ListenableFuture<Void> prerenderFramebuffers(DevicePath device, CapturePath capture, BinaryID api, int width, int height, long[] atomIndicies) {
    return myExecutorService.submit(new PrerenderFramebuffersCallable(device, capture, api, width, height, atomIndicies));
  }
  @Override
  public ListenableFuture<Path> set(Path p, Object v) {
    return myExecutorService.submit(new SetCallable(p, v));
  }

  private class FollowCallable implements Callable<Path> {
    private final CallFollow myCall;

    private FollowCallable(Path p) {
      myCall = new CallFollow();
      myCall.setP(p);
    }
    @Override
    public Path call() throws Exception {
      ResultFollow result = (ResultFollow)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class GetCallable implements Callable<Object> {
    private final CallGet myCall;

    private GetCallable(Path p) {
      myCall = new CallGet();
      myCall.setP(p);
    }
    @Override
    public Object call() throws Exception {
      ResultGet result = (ResultGet)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class GetCapturesCallable implements Callable<CapturePath[]> {
    private final CallGetCaptures myCall;

    private GetCapturesCallable() {
      myCall = new CallGetCaptures();
    }
    @Override
    public CapturePath[] call() throws Exception {
      ResultGetCaptures result = (ResultGetCaptures)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class GetDevicesCallable implements Callable<DevicePath[]> {
    private final CallGetDevices myCall;

    private GetDevicesCallable() {
      myCall = new CallGetDevices();
    }
    @Override
    public DevicePath[] call() throws Exception {
      ResultGetDevices result = (ResultGetDevices)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class GetFramebufferColorCallable implements Callable<ImageInfoPath> {
    private final CallGetFramebufferColor myCall;

    private GetFramebufferColorCallable(DevicePath device, AtomPath after, RenderSettings settings) {
      myCall = new CallGetFramebufferColor();
      myCall.setDevice(device);
      myCall.setAfter(after);
      myCall.setSettings(settings);
    }
    @Override
    public ImageInfoPath call() throws Exception {
      ResultGetFramebufferColor result = (ResultGetFramebufferColor)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class GetFramebufferDepthCallable implements Callable<ImageInfoPath> {
    private final CallGetFramebufferDepth myCall;

    private GetFramebufferDepthCallable(DevicePath device, AtomPath after) {
      myCall = new CallGetFramebufferDepth();
      myCall.setDevice(device);
      myCall.setAfter(after);
    }
    @Override
    public ImageInfoPath call() throws Exception {
      ResultGetFramebufferDepth result = (ResultGetFramebufferDepth)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class GetSchemaCallable implements Callable<Schema> {
    private final CallGetSchema myCall;

    private GetSchemaCallable() {
      myCall = new CallGetSchema();
    }
    @Override
    public Schema call() throws Exception {
      ResultGetSchema result = (ResultGetSchema)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class GetTimingInfoCallable implements Callable<TimingInfoPath> {
    private final CallGetTimingInfo myCall;

    private GetTimingInfoCallable(DevicePath device, CapturePath capture, TimingFlags flags) {
      myCall = new CallGetTimingInfo();
      myCall.setDevice(device);
      myCall.setCapture(capture);
      myCall.setFlags(flags);
    }
    @Override
    public TimingInfoPath call() throws Exception {
      ResultGetTimingInfo result = (ResultGetTimingInfo)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class ImportCaptureCallable implements Callable<CapturePath> {
    private final CallImportCapture myCall;

    private ImportCaptureCallable(String name, byte[] Data) {
      myCall = new CallImportCapture();
      myCall.setName(name);
      myCall.setData(Data);
    }
    @Override
    public CapturePath call() throws Exception {
      ResultImportCapture result = (ResultImportCapture)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
  private class PrerenderFramebuffersCallable implements Callable<Void> {
    private final CallPrerenderFramebuffers myCall;

    private PrerenderFramebuffersCallable(DevicePath device, CapturePath capture, BinaryID api, int width, int height, long[] atomIndicies) {
      myCall = new CallPrerenderFramebuffers();
      myCall.setDevice(device);
      myCall.setCapture(capture);
      myCall.setApi(api);
      myCall.setWidth(width);
      myCall.setHeight(height);
      myCall.setAtomIndicies(atomIndicies);
    }
    @Override
    public Void call() throws Exception {
      myBroadcaster.Send(myCall);
      return null;
    }
  }
  private class SetCallable implements Callable<Path> {
    private final CallSet myCall;

    private SetCallable(Path p, Object v) {
      myCall = new CallSet();
      myCall.setP(p);
      myCall.setV(v);
    }
    @Override
    public Path call() throws Exception {
      ResultSet result = (ResultSet)myBroadcaster.Send(myCall);
      return result.myValue;
    }
  }
}